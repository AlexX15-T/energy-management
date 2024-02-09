package com.example.consumer.consumer;

import com.example.consumer.entity.Device;
import com.example.consumer.entity.EnergyMeasurement;
import com.example.consumer.repository.DeviceRepository;
import com.example.consumer.repository.EnergyMeasurementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class JsonConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonConsumer.class);

    private final EnergyMeasurementRepository energyMeasurementRepository;

    private final DeviceRepository deviceRepository;

    private final SimpMessagingTemplate simpMessagingTemplate;

    public JsonConsumer(EnergyMeasurementRepository energyMeasurementRepository, DeviceRepository deviceRepository, SimpMessagingTemplate simpMessagingTemplate) {
        this.energyMeasurementRepository = energyMeasurementRepository;
        this.deviceRepository = deviceRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @RabbitListener(queues = "${rabbitmq.queue.json.name}")
    public void consumeJson(EnergyMeasurement energyMeasurement) {
        LOGGER.info("Consuming json message: {}", energyMeasurement);

        energyMeasurementRepository.save(energyMeasurement);

        LOGGER.info("Successfully saved energy measurement: {}", energyMeasurement);

        Long id = energyMeasurement.getDeviceId();
        Device currentDevice = deviceRepository.findById(id).orElse(null);

        if (currentDevice == null) {
            LOGGER.info("Device with id {} not found", id);
            return;
        }

        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        Timestamp timestampOneHourAgo = Timestamp.valueOf(oneHourAgo);
        Double lastHourConsumption = energyMeasurementRepository.sumOfConsumptionFromLastHour(id, timestampOneHourAgo);
        LOGGER.info("Last hour consumption: {}", lastHourConsumption);

        if(lastHourConsumption == null) {
            LOGGER.info("No consumption in the last hour");
            return;
        }

        if(lastHourConsumption > currentDevice.getMaximumHourlyEnergyConsumption()) {
            LOGGER.info("Device with id {} and user id {} exceeded maximum hourly energy consumption", id, currentDevice.getUser().getId());
            String destination = "/topic/user/" + currentDevice.getUser().getId() + "/notification";
            simpMessagingTemplate.convertAndSend(destination, "Device with id " + id + " exceeded maximum hourly energy consumption");
            LOGGER.info("Successfully sent energy measurement: {}", energyMeasurement);
        }
    }
}
