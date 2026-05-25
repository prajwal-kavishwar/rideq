package com.prajwal.rideq.config;

import com.prajwal.rideq.entity.Driver;
import com.prajwal.rideq.entity.User;
import com.prajwal.rideq.entity.enums.DriverStatus;
import com.prajwal.rideq.entity.enums.Role;
import com.prajwal.rideq.repository.DriverRepository;
import com.prajwal.rideq.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner init() {

        return args -> {

            // DEMO USER

            if (userRepository.findByEmail("user@rideq.com").isEmpty()) {

                User user = new User();

                user.setName("Demo User");
                user.setEmail("user@rideq.com");
                user.setPhoneNumber("9999999991");

                user.setPassword(
                        passwordEncoder.encode("user123")
                );

                user.setRole(Role.USER);

                userRepository.save(user);

                System.out.println("Demo user created");
            }

            // DEMO DRIVER

            if (driverRepository.findByEmail("driver@rideq.com").isEmpty()) {

                Driver driver = new Driver();

                driver.setName("Demo Driver");
                driver.setEmail("driver@rideq.com");
                driver.setPhoneNumber("9999999992");

                driver.setPassword(
                        passwordEncoder.encode("driver123")
                );

                driver.setVehicleNumber("MH01AB1234");

                driver.setRole(Role.DRIVER);

                driver.setStatus(DriverStatus.AVAILABLE);

                driverRepository.save(driver);

                System.out.println("Demo driver created");
            }
        };
    }
}