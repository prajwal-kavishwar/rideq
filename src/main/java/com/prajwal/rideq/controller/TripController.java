package com.prajwal.rideq.controller;


import com.prajwal.rideq.dto.CreateTripRequest;
import com.prajwal.rideq.dto.TripResponse;
import com.prajwal.rideq.service.TripService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/trips")
@RestController
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping
    public ResponseEntity<TripResponse> createTrip(
            @Valid @RequestBody CreateTripRequest request,
            Authentication authentication) {

        TripResponse response = tripService.createTrip(request, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PostMapping("/{tripId}/assign/{driverId}")
    public ResponseEntity<TripResponse> assignDriver(
            @PathVariable UUID tripId,
            @PathVariable UUID driverId) {

        TripResponse response = tripService.assignDriver(tripId, driverId);
        return ResponseEntity.ok(response);
    }


}
