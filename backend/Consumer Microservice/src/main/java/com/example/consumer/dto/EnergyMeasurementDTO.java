package com.example.consumer.dto;

import lombok.*;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EnergyMeasurementDTO {
    private Long id;
    private Timestamp timestamp;
    private Long deviceId;
    private double consumption;
}
