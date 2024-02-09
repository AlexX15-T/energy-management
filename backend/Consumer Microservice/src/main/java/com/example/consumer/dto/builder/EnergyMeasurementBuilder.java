package com.example.consumer.dto.builder;

import com.example.consumer.dto.EnergyMeasurementDTO;
import com.example.consumer.entity.EnergyMeasurement;

public class EnergyMeasurementBuilder {

    public static EnergyMeasurementDTO entityToDTO(EnergyMeasurement energyMeasurement) {
        EnergyMeasurementDTO energyMeasurementDTO = new EnergyMeasurementDTO();
        energyMeasurementDTO.setId(energyMeasurement.getId());
        energyMeasurementDTO.setTimestamp(energyMeasurement.getTimestamp());
        energyMeasurementDTO.setDeviceId(energyMeasurement.getDeviceId());
        energyMeasurementDTO.setConsumption(energyMeasurement.getConsumption());
        return energyMeasurementDTO;
    }

    public static EnergyMeasurement dtoToEntity(EnergyMeasurementDTO energyMeasurementDTO) {
        EnergyMeasurement energyMeasurement = new EnergyMeasurement();
        energyMeasurement.setTimestamp(energyMeasurementDTO.getTimestamp());
        energyMeasurement.setDeviceId(energyMeasurementDTO.getDeviceId());
        energyMeasurement.setConsumption(energyMeasurementDTO.getConsumption());
        return energyMeasurement;
    }

}
