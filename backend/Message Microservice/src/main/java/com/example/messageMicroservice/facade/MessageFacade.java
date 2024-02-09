package com.example.messageMicroservice.facade;

import com.example.messageMicroservice.dtos.MessageDTO;

import java.util.List;

public interface MessageFacade {
    List<MessageDTO> findAll();

    List<MessageDTO> findAllByReceiver(String receiver);

    List<MessageDTO> findAllBySender(String sender);

    List<MessageDTO> findAllBySenderAndReceiverSortedByTime(String sender, String receiver);

    MessageDTO save(MessageDTO messageDTO);
}
