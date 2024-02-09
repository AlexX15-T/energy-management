package com.example.consumer.service;

import com.example.consumer.dto.EnergyMeasurementDTO;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnergyMeasurementService {

    EnergyMeasurementDTO save(EnergyMeasurementDTO energyMeasurementDTO);

    List<EnergyMeasurementDTO> findByDeviceId(Long deviceId);

}
