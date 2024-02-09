package com.example.producer.dto.utility;

import com.example.producer.dto.EnergyMeasurement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class EnergyMeasurementPopulator {

    public static String toJSonString(EnergyMeasurement energyMeasurement) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String jsonString;
        try {
            jsonString = mapper.writeValueAsString(energyMeasurement);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jsonString;
    }

}
