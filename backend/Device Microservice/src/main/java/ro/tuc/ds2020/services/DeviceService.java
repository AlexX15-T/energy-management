package ro.tuc.ds2020.services;

import ro.tuc.ds2020.dtos.DeviceDTO;

import java.util.List;
import java.util.Optional;

public interface DeviceService {

    List<DeviceDTO> findAll();

    Optional<DeviceDTO> findById(Long id);

    Optional<DeviceDTO> findByAddress(String address);

    List<DeviceDTO> findByUserId(Long userId);

    Optional<DeviceDTO> save(DeviceDTO deviceDTO);

    boolean update(DeviceDTO deviceDTO);

    boolean delete(Long id);

    boolean deleteAllByUserId(Long userId);
}
