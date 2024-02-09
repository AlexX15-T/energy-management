package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ro.tuc.ds2020.entities.Device;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<Device> findById(Long Id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Device d " +
            "SET d.description = :description, " +
            "d.address = :address, " +
            "d.maximumHourlyEnergyConsumption = :maximumHourlyEnergyConsumption, " +
            "d.user.id = :userId " +
            "WHERE d.id = :id ")
    void update(Long id, String description, String address, Double maximumHourlyEnergyConsumption, Long userId);

    @Query(value = "SELECT d " +
            "FROM Device d " +
            "WHERE d.address = :address ")
    Optional<Device> findByAddress(String address);

    @Query(value = "SELECT d " +
            "FROM Device d " +
            "WHERE d.user.id = :userId ")
    List<Device> findByUserId(Long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE " +
            "FROM Device d " +
            "WHERE d.id = :id ")
    void delete(Long id);

    void deleteAllByUserId(Long userId);
}
