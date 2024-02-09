package com.example.consumer.controller;

import com.example.consumer.facade.EnergyMeasurementFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"}, maxAge = 3600)
@RestController
@RequestMapping("/monitoring")
public class MonitoringController {

    private final EnergyMeasurementFacade energyMeasurementFacade;

    public MonitoringController(EnergyMeasurementFacade energyMeasurementFacade) {
        this.energyMeasurementFacade = energyMeasurementFacade;
    }

    @GetMapping("/device/{deviceId}")
    public ResponseEntity<?> getEnergyMeasurementsByDeviceId(@PathVariable("deviceId") Long deviceId) {
        return ResponseEntity.ok(energyMeasurementFacade.findByDeviceId(deviceId));
    }
}
