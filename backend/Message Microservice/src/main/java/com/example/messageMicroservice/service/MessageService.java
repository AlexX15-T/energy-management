package com.example.messageMicroservice.service;

import com.example.messageMicroservice.dtos.MessageDTO;
import com.example.messageMicroservice.model.Message;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MessageService {

    List<MessageDTO> findAll();

    List<MessageDTO> findAllByReceiver(String receiver);

    List<MessageDTO> findAllBySender(String sender);

    List<MessageDTO> findAllBySenderAndReceiverSortedByTime(String sender, String receiver);

    MessageDTO save(MessageDTO messageDTO);
}
