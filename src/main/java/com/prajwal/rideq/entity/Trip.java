package com.prajwal.rideq.entity;

import com.prajwal.rideq.entity.enums.TripStatus;
import jakarta.persistence.*;


import java.time.Instant;
import java.util.UUID;


@Entity
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID tripId;

    @Column(nullable = false)
    private UUID userId;

    private UUID driverId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "pick_latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "pick_longitude"))
    })
    private Location pickupLocation;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "drop_latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "drop_longitude"))
    })
    private Location dropLocation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TripStatus status;

    private Double distance;

    private Double fare;
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        this.status = TripStatus.CREATED;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }



}
