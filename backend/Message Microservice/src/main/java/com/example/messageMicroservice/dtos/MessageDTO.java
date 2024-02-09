package com.example.messageMicroservice.dtos;

import java.time.LocalDateTime;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MessageDTO {
    private Long id;
    private String content;
    private String sender;
    private String receiver;
    private LocalDateTime time;

    public MessageDTO(String content, String sender, String receiver, LocalDateTime time) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.time = time;
    }
}
