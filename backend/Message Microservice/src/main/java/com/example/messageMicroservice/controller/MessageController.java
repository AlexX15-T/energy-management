package com.example.messageMicroservice.controller;

import com.example.messageMicroservice.dtos.MessageDTO;
import com.example.messageMicroservice.facade.MessageFacade;
import com.example.messageMicroservice.model.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RestController("/api/messages")
//@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"}, maxAge = 3600, allowCredentials = "true")
public class MessageController {

//    private final MessageFacade messageFacade;
//
//    public MessageController(MessageFacade messageFacade) {
//        this.messageFacade = messageFacade;
//    }
//
//    @GetMapping("/{sender}/{receiver}")
//    public ResponseEntity<?> getMessagesBySender(@PathVariable("sender") String sender, @PathVariable("receiver") String receiver) {
//        try {
//            List<MessageDTO> messages = messageFacade.findAllBySenderAndReceiverSortedByTime(sender, receiver);
//            return ResponseEntity.ok(messages);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while fetching messages.");
//        }
//    }
}
