package com.example.messageMicroservice.facade.impl;

import com.example.messageMicroservice.dtos.MessageDTO;
import com.example.messageMicroservice.service.MessageService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultMessageFacade implements com.example.messageMicroservice.facade.MessageFacade{

    private final MessageService messageService;

    public DefaultMessageFacade(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public List<MessageDTO> findAll() {
        return messageService.findAll();
    }

    @Override
    public List<MessageDTO> findAllByReceiver(String receiver) {
        return messageService.findAllByReceiver(receiver);
    }

    @Override
    public List<MessageDTO> findAllBySender(String sender) {
        return messageService.findAllBySender(sender);
    }

    @Override
    public List<MessageDTO> findAllBySenderAndReceiverSortedByTime(String sender, String receiver) {
        return messageService.findAllBySenderAndReceiverSortedByTime(sender, receiver);
    }

    @Override
    public MessageDTO save(MessageDTO messageDTO) {
        return messageService.save(messageDTO);
    }
}
