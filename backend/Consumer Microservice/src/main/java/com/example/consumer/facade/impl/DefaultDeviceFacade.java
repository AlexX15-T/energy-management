package com.example.consumer.facade.impl;

import com.example.consumer.dto.DeviceDTO;
import com.example.consumer.facade.DeviceFacade;
import com.example.consumer.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DefaultDeviceFacade implements DeviceFacade {

    private final DeviceService deviceService;

    @Autowired
    public DefaultDeviceFacade(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public List<DeviceDTO> findAll() {
        return deviceService.findAll();
    }

    @Override
    public Optional<DeviceDTO> save(DeviceDTO deviceDTO) {
        return deviceService.save(deviceDTO);
    }

    @Override
    public Optional<DeviceDTO> findById(Long id) {
        return deviceService.findById(id);
    }

    @Override
    public Optional<DeviceDTO> findByAddress(String address) {
        return deviceService.findByAddress(address);
    }

    @Override
    public List<DeviceDTO> findByUserId(Long userId) {
        return deviceService.findByUserId(userId);
    }

    @Override
    public boolean update(DeviceDTO deviceDTO) {
        return deviceService.update(deviceDTO);
    }

    @Override
    public boolean delete(Long id) {
        return deviceService.delete(id);
    }

    @Override
    public boolean deleteAllByUserId(Long userId) {
        return deviceService.deleteAllByUserId(userId);
    }
}
