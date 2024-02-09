package com.example.consumer.repository;

import com.example.consumer.entity.EnergyMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface EnergyMeasurementRepository extends JpaRepository<EnergyMeasurement, Long> {

    @Modifying
    @Transactional
    @Query("SELECT e FROM EnergyMeasurement e WHERE e.deviceId = :deviceId")
    List<EnergyMeasurement> findByDeviceId(Long deviceId);

    @Query("SELECT SUM(em.consumption) " +
            "FROM EnergyMeasurement em " +
            "WHERE em.deviceId = :deviceId " +
            "AND em.timestamp >= :oneHourAgo")
    Double sumOfConsumptionFromLastHour(Long deviceId, Timestamp oneHourAgo);

}
