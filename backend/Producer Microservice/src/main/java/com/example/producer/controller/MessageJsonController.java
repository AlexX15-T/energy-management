package com.example.producer.controller;

import com.example.producer.dto.EnergyMeasurement;
import com.example.producer.publisher.JsonProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MessageJsonController {

    private final JsonProducer jsonProducer;

    public MessageJsonController(JsonProducer jsonProducer) {
        this.jsonProducer = jsonProducer;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> sendJsonMessage(@RequestBody EnergyMeasurement energyMeasurement) {
        jsonProducer.send(energyMeasurement);
        return ResponseEntity.ok("Json Energy Measurement message sent to RabbitMQ ...!");
    }
}
