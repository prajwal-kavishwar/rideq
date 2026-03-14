package com.prajwal.rideq.dto;

import com.prajwal.rideq.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private UUID id;
    private String email;
    private Role role;
    private String token;



}
