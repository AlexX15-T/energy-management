package com.example.consumer.facade;

import com.example.consumer.dto.EnergyMeasurementDTO;

import java.util.List;

public interface EnergyMeasurementFacade {
    EnergyMeasurementDTO save(EnergyMeasurementDTO energyMeasurementDTO);

    List<EnergyMeasurementDTO> findByDeviceId(Long deviceId);
}

