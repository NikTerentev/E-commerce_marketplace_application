package org.example.lab_1.chatroom;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "chat_room", schema = "public", catalog= "e-commerce")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoom {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "chat_id", nullable = false)
    private String chatId;

    @Column(name = "sender_id", nullable = false)
    private String senderId;

    @Column(name = "recipient_id", nullable = false)
    private String recipientId;
}
