package com.prajwal.rideq.entity;


import com.prajwal.rideq.entity.enums.DriverStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;


@Getter
@NoArgsConstructor
@Document(collection="drivers")
public class Driver {
    @Id
    private UUID id;

    @Setter
    @Indexed(unique = true)
    private String email;

    @Setter
    private String name;

    @Setter
    @Indexed(unique = true)
    private String phoneNumber;

    @Setter
    private String password;

    @Setter
    private String vehicleNumber;

    @Indexed
    @Setter
    private DriverStatus status;

    @Setter
    private boolean active;

    @Setter
    private Instant createdAt;

}
