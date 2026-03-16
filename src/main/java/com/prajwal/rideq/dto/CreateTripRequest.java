package com.prajwal.rideq.dto;

import com.prajwal.rideq.entity.Location;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTripRequest {

    @NotNull
    private Location pickupLocation;

    @NotNull
    private Location dropLocation;



}
