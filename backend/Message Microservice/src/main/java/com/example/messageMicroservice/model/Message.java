package com.example.messageMicroservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Message {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String sender;

    private String receiver;

    private LocalDateTime time;

    public Message(String content, String sender, String receiver, LocalDateTime time) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.time = time;
    }
}
