package com.example.messageMicroservice.controller;

import com.example.messageMicroservice.dtos.MessageDTO;
import com.example.messageMicroservice.facade.MessageFacade;
import com.example.messageMicroservice.model.Message;
import com.example.messageMicroservice.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"}, maxAge = 3600, allowCredentials = "true")
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageFacade messageFacade;

    public ChatController(SimpMessagingTemplate messagingTemplate, MessageFacade messageFacade) {
        this.messagingTemplate = messagingTemplate;
        this.messageFacade = messageFacade;
    }


    @MessageMapping("/chat/{sender}/{receiver}")
    public void sendMessage(@Payload String chatMessage, @DestinationVariable String sender,
                            @DestinationVariable String receiver) {
        // Process the received message and handle any required logic
        // For example, you might save the message in a database, perform validations, etc.
        System.out.println("send: " + sender);
        // Construct a response message
        // Send the response message back to the specific receiver's topic
        String destination = "/topic/chat/" + sender + "/" + receiver;
        System.out.println(destination);

        MessageDTO newMessage = new MessageDTO(chatMessage, sender, receiver, LocalDateTime.now());
        messageFacade.save(newMessage);

        messagingTemplate.convertAndSend(destination, chatMessage);
    }

    @GetMapping("/api/messages")
    public ResponseEntity<?> getMessagesBySender(
            @RequestParam(name = "sender") String sender,
            @RequestParam(name = "receiver") String receiver
    ) {
        try {
            List<MessageDTO> messages = messageFacade.findAllBySenderAndReceiverSortedByTime(sender, receiver);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while fetching messages.");
        }
    }


    @GetMapping("/test2")
    public String test2() {
        return "test2";
    }
}
