package org.example.lab_1.chat;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "chat_message", schema = "public", catalog = "e-commerce")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false, length = 255)
    private String id;

    @Column(name = "chat_id", nullable = false, length = 255)
    private String chatId;

    @Column(name = "sender_id", nullable = false, length = 255)
    private String senderId;

    @Column(name = "recipient_id", nullable = false, length = 255)
    private String recipientId;

    @Column(name = "content", nullable = false, length = -1)
    private String content;

    @Column(name = "timestamp", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
}
