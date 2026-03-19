package com.prajwal.rideq.entity;

import com.prajwal.rideq.entity.enums.DriverStatus;
import com.prajwal.rideq.entity.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @Column(unique = true, nullable = false)
    private String email;
    @Setter
    @Column(name="full_name",nullable = false,length = 100)
    private String name;

    @Setter
    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Setter
    @Column(length = 100,nullable = false)
    private String password;
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Setter
    @Column(unique = true, nullable = false)
    private String vehicleNumber;

    @Setter
    @Enumerated(EnumType.STRING)
    private DriverStatus status;

    @Column(nullable = false)
    private boolean active;

    @Embedded
    @Setter
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "longitude"))
    })
    private Location currentLocation;

    @Column(nullable = false,updatable = false)
    private Instant createdAt;

    private Instant updatedAt;


    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        this.active = true;
        this.status=DriverStatus.OFFLINE;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}