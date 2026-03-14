package com.prajwal.rideq.controller;


import com.prajwal.rideq.dto.DriverResponse;
import com.prajwal.rideq.dto.LoginRequest;
import com.prajwal.rideq.dto.RegisterUserRequest;
import com.prajwal.rideq.dto.UserResponse;
import com.prajwal.rideq.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/users")
@RestController
public class UserController {
    private final UserService userService;



    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<UserResponse> register(@Valid@RequestBody RegisterUserRequest request){
        UserResponse response = userService.registerUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }


    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getByEmail(@PathVariable String email){
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<UserResponse> getByPhoneNumber(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(userService.getUserByPhoneNumber(phoneNumber));
    }
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        UserResponse response = userService.getUserByEmail(email);
        return ResponseEntity.ok(response);
    }
}
