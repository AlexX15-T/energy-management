package com.example.consumer.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    private final static Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void consume(String message) {
        LOGGER.info("Consuming message: {}", message);
    }

}
