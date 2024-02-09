package ro.tuc.ds2020.dtos.builders;

import lombok.NoArgsConstructor;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.UserRepository;


public class DeviceBuilder {
    public static DeviceDTO toDeviceDTO(Device device) {
        return new DeviceDTO(device.getId(), device.getDescription(), device.getAddress(), device.getMaximumHourlyEnergyConsumption(), device.getUser().getId());
    }

    public static Device toEntity(DeviceDTO deviceDTO) {
        return new Device(deviceDTO.getDescription(), deviceDTO.getAddress(), deviceDTO.getMaximumHourlyEnergyConsumption());
    }

}
