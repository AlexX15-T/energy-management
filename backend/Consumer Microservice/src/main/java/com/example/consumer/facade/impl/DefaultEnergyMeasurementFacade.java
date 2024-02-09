package com.example.consumer.facade.impl;

import com.example.consumer.dto.EnergyMeasurementDTO;
import com.example.consumer.facade.EnergyMeasurementFacade;
import com.example.consumer.service.EnergyMeasurementService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultEnergyMeasurementFacade implements EnergyMeasurementFacade {

    private final EnergyMeasurementService energyMeasurementService;

    public DefaultEnergyMeasurementFacade(EnergyMeasurementService energyMeasurementService) {
        this.energyMeasurementService = energyMeasurementService;
    }

    @Override
    public EnergyMeasurementDTO save(EnergyMeasurementDTO energyMeasurementDTO) {
        return energyMeasurementService.save(energyMeasurementDTO);
    }

    @Override
    public List<EnergyMeasurementDTO> findByDeviceId(Long deviceId) {
        return energyMeasurementService.findByDeviceId(deviceId);
    }
}
