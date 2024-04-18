package org.example.lab_1.controller;

import lombok.RequiredArgsConstructor;
import org.example.lab_1.chat.ChatMessage;
import org.example.lab_1.chat.ChatMessageDAO;
import org.example.lab_1.chat.ChatNotification;
import org.example.lab_1.daos.UserDAO;
import org.example.lab_1.fcm.FcmClient;
import org.example.lab_1.fcm.FcmSettings;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.example.lab_1.chat.ChatMessageDAO.save;
import static org.example.lab_1.fcm.FcmClient.sendPersonal;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void processMessage(
            @Payload ChatMessage chatMessage
    ) throws ExecutionException, InterruptedException, IOException {
        ChatMessage savedMsg = save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(), "/queue/messages",
                ChatNotification.builder()
                        .id(savedMsg.getId())
                        .senderId(savedMsg.getSenderId())
                        .recipientId(savedMsg.getRecipientId())
                        .content(savedMsg.getContent())
                        .build()
        );

        if (UserDAO.getToken(savedMsg.getRecipientId()) != null) {
            System.out.println("Сообщение отправлено.");
            sendPersonal(UserDAO.getToken(savedMsg.getRecipientId()), UserDAO.getUserByNickname(savedMsg.getSenderId()).getName(), savedMsg.getContent());
        } else {
            System.out.println("Сообщение не отправлено.");
        }
    }


    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(
            @PathVariable("senderId") String senderId,
            @PathVariable("recipientId") String recipientId
    ) {
        return ResponseEntity.ok(ChatMessageDAO.findChatMessages(senderId, recipientId));
    }
}
