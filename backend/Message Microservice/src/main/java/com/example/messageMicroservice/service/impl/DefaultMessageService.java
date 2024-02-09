package com.example.messageMicroservice.service.impl;

import com.example.messageMicroservice.dtos.MessageDTO;
import com.example.messageMicroservice.dtos.builder.MessageBuilder;
import com.example.messageMicroservice.model.Message;
import com.example.messageMicroservice.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultMessageService implements com.example.messageMicroservice.service.MessageService{

    private final MessageRepository messageRepository;

    public DefaultMessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<MessageDTO> findAll() {
        List<MessageDTO> messageDTOList = messageRepository.findAll()
                .stream()
                .map(MessageBuilder::entityToDto)
                .collect(Collectors.toList());

        return messageDTOList;
    }

    @Override
    public List<MessageDTO> findAllByReceiver(String receiver) {
        List<MessageDTO> messageDTOList = messageRepository.findAllByReceiver(receiver)
                .stream()
                .map(MessageBuilder::entityToDto)
                .collect(Collectors.toList());

        return messageDTOList;
    }

    @Override
    public List<MessageDTO> findAllBySender(String sender) {
        List<MessageDTO> messageDTOList = messageRepository.findAllBySender(sender)
                .stream()
                .map(MessageBuilder::entityToDto)
                .collect(Collectors.toList());

        return messageDTOList;
    }

    @Override
    public List<MessageDTO> findAllBySenderAndReceiverSortedByTime(String sender, String receiver) {
        List<MessageDTO> messageDTOList = messageRepository.findAllBySenderAndReceiverOrderByTime(sender, receiver)
                .stream()
                .map(MessageBuilder::entityToDto)
                .collect(Collectors.toList());

        return messageDTOList;
    }

    @Override
    public MessageDTO save(MessageDTO messageDTO) {
        Message entityToSave = MessageBuilder.dtoToEntity(messageDTO);
        Message savedEntity = messageRepository.save(entityToSave);
        return MessageBuilder.entityToDto(savedEntity);
    }
}
