package com.prajwal.rideq.entity;


import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Embeddable
public class Location {
    private double latitude;
    private double longitude;

    public Location(double latitude,double longitude){
        this.longitude=longitude;
        this.latitude=latitude;
    }

}
