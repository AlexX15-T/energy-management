package com.example.producer.dto.utility;

import com.example.producer.constants.Device;
import com.example.producer.dto.EnergyMeasurement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class CsvParser {

    private static List<String> fetchCsvData() {
        List<String> data = new ArrayList<>();

        //read csv file
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("src/main/resources/static/sensor.csv"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String line;
        String cvsSplitBy = "\n";
        try {
            while ((line = br.readLine()) != null) {
                String[] csvLine = line.split(cvsSplitBy);
                data.add(csvLine[0]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    public static List<EnergyMeasurement> convertDataToEnergyReport() {
        List<EnergyMeasurement> energyMeasurements = new ArrayList<>();
        List <String> csvLines = fetchCsvData();
        EnergyMeasurement energyMeasurement = new EnergyMeasurement();
        energyMeasurement.setDeviceId(Device.ID);

        for (String csvLine : csvLines) {
            energyMeasurement.setTimestamp(java.sql.Timestamp.valueOf(LocalDateTime.now()));
            energyMeasurement.setConsumption(Double.parseDouble(csvLine));
            energyMeasurements.add(energyMeasurement);
        }

        return energyMeasurements;
    }

    public static void main(String[] args) {

        List<EnergyMeasurement> energyMeasurements = convertDataToEnergyReport();
        for (EnergyMeasurement energyMeasurement : energyMeasurements) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            RequestSender.post(EnergyMeasurementPopulator.toJSonString(energyMeasurement));
            System.out.println(energyMeasurement);
        }
    }
}
