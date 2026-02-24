package com.prajwal.rideq.dto;


import com.prajwal.rideq.entity.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class UserResponse {
    private UUID id;

    private String name;

    private String email;

    private String phoneNumber;

    private Role role;

    private boolean active;

    private Instant createdAt;


}
