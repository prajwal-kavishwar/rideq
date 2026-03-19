package com.prajwal.rideq.repository;

import com.prajwal.rideq.dto.TripResponse;
import com.prajwal.rideq.entity.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip,UUID> {
    Optional<Trip> findByTripId(UUID id);
    Boolean existsByTripId(UUID id);

    Page<Trip> findByUserId(UUID userId, Pageable pageable);
    Page<Trip> findByDriverId(UUID driverId,Pageable pageable);
}
