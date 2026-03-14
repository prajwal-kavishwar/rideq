package com.prajwal.rideq.dto;

import com.prajwal.rideq.entity.Location;
import com.prajwal.rideq.entity.enums.TripStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TripResponse {

    private UUID id;
    private UUID userId;
    private UUID driverId;
    private Location pickUpLocation;
    private Location dropLocation;
    private TripStatus status;
    private Double distance;
    private Double fare;


}
