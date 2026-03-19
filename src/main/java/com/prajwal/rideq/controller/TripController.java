package com.prajwal.rideq.controller;


import com.prajwal.rideq.dto.CreateTripRequest;
import com.prajwal.rideq.dto.TripResponse;
import com.prajwal.rideq.service.TripService;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @PostMapping("/{tripId}/accept")
    public ResponseEntity<TripResponse> acceptTrip(@PathVariable UUID tripId,
                                                   Authentication authentication ){
        String email= authentication.getName();
        TripResponse response=tripService.acceptTrip(tripId,email);
        return ResponseEntity.ok(response);

    }
    @PostMapping("/{tripId}/start")
    public ResponseEntity<TripResponse> startTrip(@PathVariable UUID tripId,
                                                  Authentication authentication){
        String email=authentication.getName();
        TripResponse response=tripService.startTrip(tripId,email);

        return ResponseEntity.ok(response);

    }
    @PostMapping("/{tripId}/complete")
    public ResponseEntity<TripResponse> completeTrip(@PathVariable UUID tripId,
                                                  Authentication authentication){
        String email=authentication.getName();
        TripResponse response=tripService.completeTrip(tripId,email);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/{tripId}/detail")
    public ResponseEntity<TripResponse> tripDetail(
            @PathVariable UUID tripId) {

        return ResponseEntity.ok(tripService.getTripDetail(tripId));

    }
    @PostMapping("/{tripId}/cancel")
    public ResponseEntity<TripResponse> cancelTrip(@PathVariable UUID tripId,
                                                   Authentication authentication){
        String email = authentication.getName();

        TripResponse response = tripService.cancelTrip(tripId, email);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    public Page<TripResponse> getUserTrips(Authentication authentication,
    @RequestParam(defaultValue = "0")int page,
    @RequestParam(defaultValue = "5")int size){
        return tripService.getUserTrips(authentication,page,size);
    }
    @GetMapping("/driver")
    public Page<TripResponse> getDriverTrips(
            Authentication authentication,
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "5")int size){
        return tripService.getDriverTrips(authentication,page,size);
    }

}
