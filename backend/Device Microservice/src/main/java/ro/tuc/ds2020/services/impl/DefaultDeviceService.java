package ro.tuc.ds2020.services.impl;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.DeviceRepository;
import ro.tuc.ds2020.repositories.UserRepository;
import ro.tuc.ds2020.services.DeviceService;

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
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }

        return Optional.of(DeviceBuilder.toDeviceDTO(device.get()));
    }

    @Transactional
    @Override
    public Optional<DeviceDTO> findByAddress(String address) {
        Optional<Device> device  = deviceRepository.findByAddress(address);

        if(!device.isPresent()) {
            LOGGER.error("Device with address {} was not found in db", address);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with address: " + address);
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
            throw new ResourceNotFoundException("User id is null");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

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
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + deviceDTO.getId());
        }

        Device device = DeviceBuilder.toEntity(deviceDTO);
        device.setId(deviceDTO.getId());
        User updatedUser = userRepository.findById(deviceDTO.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
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
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
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
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with userId: " + userId);
        }

        deviceRepository.deleteAllByUserId(userId);
        return true;
    }

}
