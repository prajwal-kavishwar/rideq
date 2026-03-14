package com.prajwal.rideq.mapper;

import com.prajwal.rideq.dto.DriverResponse;
import com.prajwal.rideq.entity.Driver;

public class DriverMapper {
    public static DriverResponse toResponse(Driver driver){
        DriverResponse response=new DriverResponse();
        response.setId(driver.getId());
        response.setName(driver.getName());
        response.setEmail(driver.getEmail());
        response.setPhoneNumber(driver.getPhoneNumber());
        response.setDriverStatus(driver.getStatus());
        response.setVehicleNumber(driver.getVehicleNumber());
        response.setActive(driver.isActive());
        response.setRole(driver.getRole());
        response.setCreatedAt(driver.getCreatedAt());
        return response;
    }
}
