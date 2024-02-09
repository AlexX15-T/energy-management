package com.example.producer.publisher;

import com.example.producer.dto.EnergyMeasurement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JsonProducer {

    private final static Logger LOGGER = LoggerFactory.getLogger(JsonProducer.class);

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;

    private final RabbitTemplate rabbitTemplate;

    public JsonProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(EnergyMeasurement energyMeasurement) {
        LOGGER.info("Sending message: {}", energyMeasurement);
        rabbitTemplate.convertAndSend(exchange, routingJsonKey, energyMeasurement);
    }
}
