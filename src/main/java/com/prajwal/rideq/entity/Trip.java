package com.prajwal.rideq.entity;

import com.prajwal.rideq.entity.enums.TripStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.Instant;
import java.util.UUID;

//
//need to add
//       acceptedAt
//        startedAt
//        completedAt

@Entity
@Table(name = "trips")
public class Trip {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID tripId;
    @Getter
    @Setter
    @Column(nullable = false)
    private UUID userId;
    @Getter
    @Setter
    private UUID driverId;
    @Getter
    @Setter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "pick_latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "pick_longitude"))
    })
    private Location pickupLocation;

    @Getter
    @Setter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "drop_latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "drop_longitude"))
    })
    private Location dropLocation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Getter
    @Setter
    private TripStatus status;
    @Getter
    @Setter
    private Double distance;
    @Getter
    @Setter
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
