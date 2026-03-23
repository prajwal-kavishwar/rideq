package com.prajwal.rideq.listener;


import com.prajwal.rideq.event.TripCreatedEvent;
import com.prajwal.rideq.service.TripService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TripEventListener {
    private TripService tripService;
    public TripEventListener(TripService tripService) {
        this.tripService = tripService;
    }




    @Async
    @EventListener
    public void handleTripCreated(TripCreatedEvent event) {
        System.out.println("EVENT RECEIVED: " + event.getTripId());
        tripService.assignNextDriver(event.getTripId());
    }


}
