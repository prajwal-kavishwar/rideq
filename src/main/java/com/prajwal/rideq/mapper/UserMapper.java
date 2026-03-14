package com.prajwal.rideq.mapper;

import com.prajwal.rideq.dto.UserResponse;
import com.prajwal.rideq.entity.User;

public class UserMapper {

    public static UserResponse toResponse(User user) {

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setRole(user.getRole());
        response.setActive(user.isActive());
        response.setCreatedAt(user.getCreatedAt());

        return response;
    }
}