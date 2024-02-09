package com.example.consumer.entity;

import jakarta.persistence.*;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "maximum_hourly_energy_consumption", nullable = false)
    private Double maximumHourlyEnergyConsumption;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Device(String description, String address, Double maximumHourlyEnergyConsumption, User user) {
        this.description = description;
        this.address = address;
        this.maximumHourlyEnergyConsumption = maximumHourlyEnergyConsumption;
        this.user = user;
    }

    public Device(String description, String address, Double maximumHourlyEnergyConsumption){
        this.description = description;
        this.address = address;
        this.maximumHourlyEnergyConsumption = maximumHourlyEnergyConsumption;
    }
}
