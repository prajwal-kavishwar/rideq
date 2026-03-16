package com.prajwal.rideq.service;


import com.prajwal.rideq.dto.CreateTripRequest;
import com.prajwal.rideq.dto.TripResponse;
import com.prajwal.rideq.entity.Driver;
import com.prajwal.rideq.entity.Trip;

import com.prajwal.rideq.entity.User;
import com.prajwal.rideq.entity.enums.TripStatus;
import com.prajwal.rideq.exception.ResourceNotFoundException;
import com.prajwal.rideq.repository.DriverRepository;
import com.prajwal.rideq.repository.TripRepository;
import com.prajwal.rideq.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TripService {
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;

    public TripService(TripRepository tripRepository, UserRepository userRepository, DriverRepository driverRepository) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
        this.driverRepository = driverRepository;
    }

    public TripResponse createTrip(CreateTripRequest request, Authentication authentication){
        Trip trip=new Trip();
        trip.setPickupLocation(request.getPickupLocation());
        trip.setDropLocation(request.getDropLocation());



        String email = authentication.getName();


        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        trip.setUserId(user.getId());

        Trip savedTrip = tripRepository.save(trip);
        TripResponse response=new TripResponse();
        response.setId(savedTrip.getTripId());
        response.setUserId(savedTrip.getUserId());
        response.setDriverId(savedTrip.getDriverId());
        response.setStatus(savedTrip.getStatus());
        response.setPickUpLocation(savedTrip.getPickupLocation());
        response.setDropLocation((savedTrip.getDropLocation()));
        response.setDistance(savedTrip.getDistance());
        response.setFare(savedTrip.getFare());

        return response;


    }

    public TripResponse assignDriver(UUID tripId, UUID driverId){
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found"));

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));

        if (trip.getStatus() != TripStatus.CREATED) {
            throw new IllegalStateException("Trip cannot be assigned");
        }

        trip.setDriverId(driver.getId());
        trip.setStatus(TripStatus.ASSIGNED);

        Trip savedTrip = tripRepository.save(trip);

        TripResponse response = new TripResponse();
        response.setId(savedTrip.getTripId());
        response.setUserId(savedTrip.getUserId());
        response.setDriverId(savedTrip.getDriverId());
        response.setStatus(savedTrip.getStatus());
        response.setPickUpLocation(savedTrip.getPickupLocation());
        response.setDropLocation(savedTrip.getDropLocation());
        response.setDistance(savedTrip.getDistance());
        response.setFare(savedTrip.getFare());

        return response;
    }
}
