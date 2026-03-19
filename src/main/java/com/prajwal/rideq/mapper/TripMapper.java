package com.prajwal.rideq.mapper;

import com.prajwal.rideq.dto.TripResponse;
import com.prajwal.rideq.entity.Trip;

public class TripMapper {
    public static TripResponse toResponse(Trip savedTrip){
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
}
