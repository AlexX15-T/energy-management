package com.example.messageMicroservice.dtos.builder;

import com.example.messageMicroservice.dtos.MessageDTO;
import com.example.messageMicroservice.model.Message;

public class MessageBuilder {
    public static MessageDTO entityToDto(Message entity) {
        MessageDTO dto = new MessageDTO();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setSender(entity.getSender());
        dto.setReceiver(entity.getReceiver());
        dto.setTime(entity.getTime());
        return dto;
    }

    public static Message dtoToEntity(MessageDTO dto) {
        Message entity = new Message();
        entity.setContent(dto.getContent());
        entity.setSender(dto.getSender());
        entity.setReceiver(dto.getReceiver());
        entity.setTime(dto.getTime());
        return entity;
    }
}
