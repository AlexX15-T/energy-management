package com.example.consumer.service.impl;

import com.example.consumer.dto.DeviceDTO;
import com.example.consumer.dto.builder.DeviceBuilder;
import com.example.consumer.entity.Device;
import com.example.consumer.entity.User;
import com.example.consumer.repository.DeviceRepository;
import com.example.consumer.repository.UserRepository;
import com.example.consumer.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultDeviceService implements DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDeviceService.class);
    private final DeviceRepository deviceRepository;

    private final UserRepository userRepository;

    @Autowired
    public DefaultDeviceService(DeviceRepository deviceRepository, UserRepository userRepository) {
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
    }

    public List<DeviceDTO> findAll() {
        List<DeviceDTO> deviceDTOList = deviceRepository.findAll().stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());

        return deviceDTOList;
    }

    @Transactional
    @Override
    public Optional<DeviceDTO> findById(Long id) {
        Optional<Device> device  = deviceRepository.findById(id);

        if(!device.isPresent()) {
            LOGGER.error("Device with id {} was not found in db", id);
        }

        return Optional.of(DeviceBuilder.toDeviceDTO(device.get()));
    }

    @Transactional
    @Override
    public Optional<DeviceDTO> findByAddress(String address) {
        Optional<Device> device  = deviceRepository.findByAddress(address);

        if(!device.isPresent()) {
            LOGGER.error("Device with address {} was not found in db", address);
        }

        return Optional.of(DeviceBuilder.toDeviceDTO(device.get()));
    }

    @Transactional
    @Override
    public List<DeviceDTO> findByUserId(Long userId) {
        List<DeviceDTO> deviceDTOList = deviceRepository.findByUserId(userId).stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());

        return deviceDTOList;
    }

    @Transactional
    @Override
    public Optional<DeviceDTO> save(DeviceDTO deviceDTO) {
        Long userId = deviceDTO.getUserId();

        if(userId == null) {
            LOGGER.error("User id is null");
        }

        User user = userRepository.findById(userId).get();

        Device device = DeviceBuilder.toEntity(deviceDTO);
        device.setUser(user);
        device = deviceRepository.save(device);
        return Optional.of(DeviceBuilder.toDeviceDTO(device));
    }

    @Transactional
    @Override
    public boolean update(DeviceDTO deviceDTO) {
        Optional<Device> deviceOptional = deviceRepository.findById(deviceDTO.getId());

        if(!deviceOptional.isPresent()) {
            LOGGER.error("Device with id {} was not found in db", deviceDTO.getId());
        }

        Device device = DeviceBuilder.toEntity(deviceDTO);
        device.setId(deviceDTO.getId());
        User updatedUser = userRepository.findById(deviceDTO.getUserId()).get();
        device.setUser(updatedUser);

        deviceRepository.update(device.getId(), device.getDescription(),
                device.getAddress(), device.getMaximumHourlyEnergyConsumption(), device.getUser().getId());
        return true;
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);

        if(!deviceOptional.isPresent()) {
            LOGGER.error("Device with id {} was not found in db", id);
        }

        deviceRepository.delete(id);
        return true;
    }

    @Transactional
    @Override
    public boolean deleteAllByUserId(Long userId) {
        List<Device> deviceList = deviceRepository.findByUserId(userId);

        if(deviceList.isEmpty()) {
            LOGGER.error("Devices with userId {} was not found in db", userId);
        }

        deviceRepository.deleteAllByUserId(userId);
        return true;
    }

}
