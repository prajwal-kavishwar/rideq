package com.prajwal.rideq.repository;

import com.prajwal.rideq.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip,UUID> {
    Optional<Trip> findByTripId(UUID id);
    Boolean existsByTripId(UUID id);


}
