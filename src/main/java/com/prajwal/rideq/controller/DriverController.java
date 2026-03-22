package com.prajwal.rideq.controller;


import com.prajwal.rideq.dto.DriverResponse;
import com.prajwal.rideq.dto.RegisterDriverRequest;
import com.prajwal.rideq.dto.TripResponse;
import com.prajwal.rideq.service.DriverService;
import com.prajwal.rideq.service.TripService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RequestMapping("/api/drivers")
@RestController
public class DriverController {
    private DriverService driverService;

    public DriverController(DriverService driverService, TripService tripService){
        this.driverService=driverService;
        this.tripService = tripService;
    }
    public final TripService tripService;

    @PostMapping
    public ResponseEntity<DriverResponse> register(@Valid@RequestBody RegisterDriverRequest request){
        DriverResponse response=driverService.registerDriver(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<DriverResponse> getById(@PathVariable UUID id){
        return ResponseEntity.ok(driverService.getDriverById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<DriverResponse> getByEmail(@PathVariable String email){
        return ResponseEntity.ok(driverService.getDriverByEmail(email));
    }


    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<DriverResponse> getByPhoneNumber(@PathVariable String phoneNumber){
        return ResponseEntity.ok(driverService.getDriverByPhoneNumber(phoneNumber));
    }

    @GetMapping("/me")
    public ResponseEntity<DriverResponse> getCurrentDriver(){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        DriverResponse response = driverService.getDriverByEmail(email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/trip/{tripId}/accept")
    public TripResponse acceptTrip(@PathVariable UUID tripId,
                                   Authentication authentication) {

        return tripService.acceptTrip(tripId, authentication.getName());
    }

    @PostMapping("/trip/{tripId}/reject")
    public void rejectTrip(@PathVariable UUID tripId,
                           Authentication authentication) {

        tripService.rejectTrip(tripId, authentication.getName());
    }



}
