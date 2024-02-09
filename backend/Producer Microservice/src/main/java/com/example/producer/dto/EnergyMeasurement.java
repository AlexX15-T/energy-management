package com.example.producer.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EnergyMeasurement implements Serializable {
    private Timestamp timestamp;
    private Long deviceId;
    private double consumption;
}
