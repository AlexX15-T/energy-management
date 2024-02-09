package com.example.consumer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Entity
public class EnergyMeasurement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @Column(name = "device_id", nullable = false)
    private Long deviceId;

    @Column(name = "consumption", nullable = false)
    private double consumption;
}
