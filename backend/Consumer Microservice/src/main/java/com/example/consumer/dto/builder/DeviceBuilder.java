package com.example.consumer.dto.builder;

import com.example.consumer.dto.DeviceDTO;
import com.example.consumer.entity.Device;


public class DeviceBuilder {
    public static DeviceDTO toDeviceDTO(Device device) {
        return new DeviceDTO(device.getId(), device.getDescription(), device.getAddress(), device.getMaximumHourlyEnergyConsumption(), device.getUser().getId());
    }

    public static Device toEntity(DeviceDTO deviceDTO) {
        return new Device(deviceDTO.getDescription(), deviceDTO.getAddress(), deviceDTO.getMaximumHourlyEnergyConsumption());
    }

}
