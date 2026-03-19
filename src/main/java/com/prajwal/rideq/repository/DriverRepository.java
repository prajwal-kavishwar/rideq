package com.prajwal.rideq.repository;

import com.prajwal.rideq.entity.Driver;
import com.prajwal.rideq.entity.enums.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DriverRepository extends JpaRepository<Driver, UUID> {

    Optional<Driver> findByEmail(String email);
    Optional<Driver> findByPhoneNumber(String phoneNumber);
    List<Driver> findByStatus(DriverStatus status);

    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);


}
