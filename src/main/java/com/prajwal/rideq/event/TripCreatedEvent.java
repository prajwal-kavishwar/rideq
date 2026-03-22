package com.prajwal.rideq.event;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;


@AllArgsConstructor
@Getter
public class TripCreatedEvent {
    private UUID tripId;

}
