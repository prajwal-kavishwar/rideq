package com.prajwal.rideq.controller;


import com.prajwal.rideq.dto.DriverResponse;
import com.prajwal.rideq.dto.RegisterDriverRequest;
import com.prajwal.rideq.dto.TripResponse;
import com.prajwal.rideq.entity.Driver;
import com.prajwal.rideq.entity.Location;
import com.prajwal.rideq.entity.enums.DriverStatus;
import com.prajwal.rideq.exception.ResourceNotFoundException;
import com.prajwal.rideq.mapper.DriverMapper;
import com.prajwal.rideq.repository.DriverRepository;
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
    private DriverRepository driverRepository;

    public DriverController(DriverService driverService, TripService tripService,DriverRepository driverRepository){
        this.driverService=driverService;
        this.tripService = tripService;
        this.driverRepository=driverRepository;
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
    @PostMapping("/location")
    public ResponseEntity<DriverResponse> setLocation(
            @RequestBody Location location,
            Authentication authentication
    ) {
        return ResponseEntity.ok(driverService.setLocation(authentication, location));
    }

    @PostMapping("/online")
    public ResponseEntity<DriverResponse> goOnline(
            Authentication authentication
    ) {
        Driver driver=driverRepository.findByEmail(authentication.getName())
                .orElseThrow(()-> new ResourceNotFoundException("Driver not found"));
        driver.setStatus(DriverStatus.AVAILABLE);
        driverRepository.save(driver);
        return ResponseEntity.ok(DriverMapper.toResponse(driver));
    }




}
