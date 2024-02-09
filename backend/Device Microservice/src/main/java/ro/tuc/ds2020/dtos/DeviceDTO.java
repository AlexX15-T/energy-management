package ro.tuc.ds2020.dtos;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class DeviceDTO extends RepresentationModel<DeviceDTO> {
    private Long id;
    private String description;
    private String address;
    private Double maximumHourlyEnergyConsumption;
    private Long userId;


    public DeviceDTO(String description, String address, Double maximumHourlyEnergyConsumption, Long userId) {
        this.description = description;
        this.address = address;
        this.maximumHourlyEnergyConsumption = maximumHourlyEnergyConsumption;
        this.userId = userId;
    }
}
