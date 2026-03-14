package com.prajwal.rideq.auth;


import com.prajwal.rideq.dto.LoginRequest;
import com.prajwal.rideq.dto.LoginResponse;

import com.prajwal.rideq.entity.Driver;
import com.prajwal.rideq.entity.User;
import com.prajwal.rideq.exception.ResourceNotFoundException;
import com.prajwal.rideq.repository.DriverRepository;
import com.prajwal.rideq.repository.UserRepository;
import com.prajwal.rideq.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public AuthService(UserRepository userRepository,
                       DriverRepository driverRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {

        this.driverRepository = driverRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request){


        Optional<User> userOptioanl=userRepository.findByEmail(request.getEmail());

        if(userOptioanl.isPresent()){
            User user=userOptioanl.get();
            if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
                throw new RuntimeException("Invalid email or password");
            }
            String token = jwtService.generateToken(user.getEmail());

            return new LoginResponse(
                    user.getId(),
                    user.getEmail(),
                    user.getRole(),
                    token
            );
        }
        Optional<Driver> driverOptional = driverRepository.findByEmail(request.getEmail());

        if(driverOptional.isPresent()){
            Driver driver=driverOptional.get();
            if(!passwordEncoder.matches(request.getPassword(),driver.getPassword())){
                throw new RuntimeException("Invalid email or password");
            }
            String token = jwtService.generateToken(driver.getEmail());

            return new LoginResponse(
                    driver.getId(),
                    driver.getEmail(),
                    driver.getRole(),
                    token
            );

        }
        throw new ResourceNotFoundException("User or Driver not found");
    }



}
