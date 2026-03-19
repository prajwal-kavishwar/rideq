package com.prajwal.rideq.service;

import com.prajwal.rideq.dto.DriverResponse;
import com.prajwal.rideq.dto.RegisterDriverRequest;
import com.prajwal.rideq.entity.Driver;
import com.prajwal.rideq.entity.Location;
import com.prajwal.rideq.entity.enums.DriverStatus;
import com.prajwal.rideq.entity.enums.Role;
import com.prajwal.rideq.exception.DuplicateResourceException;
import com.prajwal.rideq.exception.ResourceNotFoundException;
import com.prajwal.rideq.mapper.DriverMapper;
import com.prajwal.rideq.repository.DriverRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final PasswordEncoder passwordEncoder;

    public DriverService(DriverRepository driverRepository, PasswordEncoder passwordEncoder) {
        this.driverRepository = driverRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public DriverResponse getDriverById(UUID id){
        Driver driver=driverRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Driver not found"));
        return DriverMapper.toResponse(driver);
    }
    public DriverResponse getDriverByEmail(String email){
        Driver driver=driverRepository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("Driver not found with email : "+email));
        return DriverMapper.toResponse(driver);
    }
    public DriverResponse getDriverByPhoneNumber(String phoneNumber){
        Driver driver=driverRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(()->new ResourceNotFoundException("Driver not found with phone number : "+phoneNumber));
        return DriverMapper.toResponse(driver);
    }
    public DriverResponse registerDriver(RegisterDriverRequest request){
        if(driverRepository.existsByEmail(request.getEmail())){
            throw new DuplicateResourceException("Email already exists");
        }

        if(driverRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new DuplicateResourceException("Phone number already exists");
        }
        Driver driver=new Driver();
        driver.setEmail(request.getEmail());
        driver.setRole(Role.DRIVER);
        driver.setName(request.getName());
        driver.setStatus(DriverStatus.OFFLINE);
        driver.setPassword(passwordEncoder.encode(request.getPassword()));
        driver.setPhoneNumber(request.getPhoneNumber());
        driver.setVehicleNumber(request.getVehicleNumber());
        driverRepository.save(driver);

        return DriverMapper.toResponse(driver);
    }

    public DriverResponse setLocation(Authentication authentication,Location location){
        Driver driver=driverRepository.findByEmail(authentication.getName())
                .orElseThrow(()->new ResourceNotFoundException("Driver not found"));
        Location newLocation = new Location(
                location.getLatitude(),
                location.getLongitude()
        );

        driver.setCurrentLocation(newLocation);
        driverRepository.save(driver);
        return DriverMapper.toResponse(driver);

    }

}
