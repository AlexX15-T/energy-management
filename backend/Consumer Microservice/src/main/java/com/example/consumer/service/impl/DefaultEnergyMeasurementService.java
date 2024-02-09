package com.example.consumer.service.impl;

import com.example.consumer.dto.EnergyMeasurementDTO;
import com.example.consumer.dto.builder.EnergyMeasurementBuilder;
import com.example.consumer.entity.EnergyMeasurement;
import com.example.consumer.repository.EnergyMeasurementRepository;
import com.example.consumer.service.EnergyMeasurementService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultEnergyMeasurementService implements EnergyMeasurementService {

    private final EnergyMeasurementRepository energyMeasurementRepository;

    public DefaultEnergyMeasurementService(EnergyMeasurementRepository energyMeasurementRepository) {
        this.energyMeasurementRepository = energyMeasurementRepository;
    }

    @Override
    public EnergyMeasurementDTO save(EnergyMeasurementDTO energyMeasurementDTO) {
        EnergyMeasurement toSave = EnergyMeasurementBuilder.dtoToEntity(energyMeasurementDTO);
        EnergyMeasurement saved = energyMeasurementRepository.save(toSave);
        return EnergyMeasurementBuilder.entityToDTO(saved);
    }

    @Override
    public List<EnergyMeasurementDTO> findByDeviceId(Long deviceId) {
        return energyMeasurementRepository.findByDeviceId(deviceId)
                .parallelStream()
                .map(EnergyMeasurementBuilder::entityToDTO)
                .collect(Collectors.toList());
    }
}
