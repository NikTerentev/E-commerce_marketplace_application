package org.example.lab_1.chatroom;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

import static org.example.lab_1.manager.DAO.*;

public class ChatRoomDAO {
    public static Optional<String> getChatRoomId(
            String senderId,
            String recipientId,
            boolean createNewRoomIfNotExists
    ) {
        // Получаем CriteriaBuilder
        CriteriaBuilder cb = getSession().getCriteriaBuilder();

        // Создаем CriteriaQuery
        CriteriaQuery<String> cq = cb.createQuery(String.class);

        // Определяем корневую сущность
        Root<ChatRoom> root = cq.from(ChatRoom.class);

        // Формируем условие запроса
        Predicate senderIdPredicate = cb.equal(root.get("senderId"), senderId);
        Predicate recipientIdPredicate = cb.equal(root.get("recipientId"), recipientId);
        cq.where(senderIdPredicate, recipientIdPredicate);

        // Выбираем поле для возврата
        cq.select(root.get("chatId"));

        // Пытаемся получить результат запроса
        String chatId = getSession().createQuery(cq).uniqueResult();

        if (chatId != null) {
            return Optional.of(chatId);
        } else if (createNewRoomIfNotExists) {
            // Если комната чата не найдена и разрешено создание новой комнаты
            chatId = createChatId(senderId, recipientId);
            return Optional.of(chatId);
        }
        return Optional.empty();
    }

    private static String createChatId(String senderId, String recipientId) {
        String chatId = String.format("%s_%s", senderId, recipientId);

        ChatRoom senderRecipient = ChatRoom.builder()
                .chatId(chatId)
                .senderId(senderId)
                .recipientId(recipientId)
                .build();

        ChatRoom recipientSender = ChatRoom.builder()
                .chatId(chatId)
                .senderId(recipientId)
                .recipientId(senderId)
                .build();

        begin();
        getSession().save(senderRecipient);
        getSession().save(recipientSender);
        commit();

        return chatId;
    }
}
