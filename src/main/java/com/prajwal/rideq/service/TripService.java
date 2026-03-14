package com.prajwal.rideq.service;


import com.prajwal.rideq.dto.CreateTripRequest;
import com.prajwal.rideq.dto.RegisterDriverRequest;
import com.prajwal.rideq.dto.TripResponse;
import com.prajwal.rideq.entity.Trip;
import com.prajwal.rideq.entity.enums.TripStatus;
import com.prajwal.rideq.repository.TripRepository;
import org.springframework.stereotype.Service;

@Service
public class TripService {
    private final TripRepository tripRepository;


    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public TripResponse CreateTrip(CreateTripRequest request){
        Trip trip=new Trip();
        trip.setPickupLocation(request.getPickupLocation());
        trip.setDropLocation(request.getDropLocation());

        Trip savedTrip = tripRepository.save(trip);

        TripResponse response=new TripResponse();
        response.setId(savedTrip.getTripId());
        response.setUserId(savedTrip.getUserId());
        response.setDriverId(savedTrip.getDriverId());
        response.setStatus(savedTrip.getStatus());
        response.setPickUpLocation(savedTrip.getPickupLocation());
        response.setDropLocation((savedTrip.getPickupLocation()));
        response.setDistance(savedTrip.getDistance());
        response.setFare(savedTrip.getFare());

        return response;


    }
}
