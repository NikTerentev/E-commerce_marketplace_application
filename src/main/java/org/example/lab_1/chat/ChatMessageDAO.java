package org.example.lab_1.chat;

import org.example.lab_1.exceptions.NotFoundException;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static org.example.lab_1.chatroom.ChatRoomDAO.getChatRoomId;
import static org.example.lab_1.manager.DAO.*;

public class ChatMessageDAO {

    public static ChatMessage save(ChatMessage chatMessage) {
        String chatId = getChatRoomId(
                chatMessage.getSenderId(),
                chatMessage.getRecipientId(),
                true
        ).orElseThrow();
        chatMessage.setChatId(chatId);
        begin();
        getSession().save(chatMessage);
        commit();
        return chatMessage;
    }

    public static List<ChatMessage> findChatMessages(
            String senderId,
            String recipientId
    ) {
       var chatId = getChatRoomId(
               senderId,
               recipientId,
               false);
       return chatId.map(ChatMessageDAO::findByChatId).orElse(new ArrayList<>());
    }

    public static List<ChatMessage> findByChatId(String chatId) {
        begin();
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<ChatMessage> query = builder.createQuery(ChatMessage.class);
        Root<ChatMessage> root = query.from(ChatMessage.class);
        query.select(root).where(builder.equal(root.get("chatId"), chatId));
        Query<ChatMessage> q = getSession().createQuery(query);
        List<ChatMessage> chatMessages = q.getResultList();
        commit();
        return chatMessages;
    }
}
