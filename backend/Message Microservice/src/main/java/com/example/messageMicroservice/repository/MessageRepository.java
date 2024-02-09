package com.example.messageMicroservice.repository;

import com.example.messageMicroservice.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByReceiver(String receiver);

    List<Message> findAllBySender(String sender);

    List<Message> findAllBySenderAndReceiver(String sender, String receiver);

    @Query(value = "SELECT m " +
            "FROM Message m " +
            "WHERE m.sender = :sender AND m.receiver = :receiver " +
            "ORDER BY m.time DESC")
    List<Message> findAllBySenderAndReceiverOrderByTime(String sender, String receiver);
}
