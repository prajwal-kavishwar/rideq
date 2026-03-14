package com.prajwal.rideq.dto;

import com.prajwal.rideq.entity.enums.DriverStatus;

import com.prajwal.rideq.entity.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class DriverResponse {
    private UUID id;
    private String name;
    private String email;
    private String phoneNumber;
    private DriverStatus driverStatus;
    private String vehicleNumber;
    private Role role;
    private boolean active;
    private Instant createdAt;

}
