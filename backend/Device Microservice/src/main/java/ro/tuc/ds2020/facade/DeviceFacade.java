package ro.tuc.ds2020.facade;

import ro.tuc.ds2020.dtos.DeviceDTO;

import java.util.List;
import java.util.Optional;

public interface DeviceFacade {
    List <DeviceDTO> findAll();

    Optional<DeviceDTO> save(DeviceDTO deviceDTO);

    Optional<DeviceDTO> findById(Long id);

    Optional<DeviceDTO> findByAddress(String address);

    List <DeviceDTO> findByUserId(Long userId);

    boolean update(DeviceDTO deviceDTO);

    boolean delete(Long id);

    boolean deleteAllByUserId(Long userId);
}
