package com.prajwal.rideq.service;


import com.prajwal.rideq.dto.CreateTripRequest;
import com.prajwal.rideq.dto.TripResponse;
import com.prajwal.rideq.entity.Driver;
import com.prajwal.rideq.entity.Trip;

import com.prajwal.rideq.entity.User;
import com.prajwal.rideq.entity.enums.DriverStatus;
import com.prajwal.rideq.entity.enums.TripStatus;
import com.prajwal.rideq.event.TripCreatedEvent;
import com.prajwal.rideq.exception.ResourceNotFoundException;
import com.prajwal.rideq.mapper.TripMapper;
import com.prajwal.rideq.repository.DriverRepository;
import com.prajwal.rideq.repository.TripRepository;
import com.prajwal.rideq.repository.UserRepository;
import com.prajwal.rideq.util.DistanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TripService {


    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;
    

    public TripService(TripRepository tripRepository, UserRepository userRepository, DriverRepository driverRepository) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
        this.driverRepository = driverRepository;
    }

    public TripResponse createTrip(CreateTripRequest request, Authentication authentication){
        Trip trip=new Trip();
        trip.setPickupLocation(request.getPickupLocation());
        trip.setDropLocation(request.getDropLocation());



        String email = authentication.getName();


        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        trip.setUserId(user.getId());
        setFareAndDistance(trip);
        trip.setStatus(TripStatus.CREATED);


        Trip savedTrip = tripRepository.save(trip);

        eventPublisher.publishEvent(new TripCreatedEvent(savedTrip.getTripId()));

        return TripMapper.toResponse(savedTrip);


    }

    public TripResponse assignDriver(UUID tripId, UUID driverId){
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found"));

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));

        if (trip.getStatus() != TripStatus.CREATED) {
            throw new IllegalStateException("Trip cannot be assigned");
        }

        trip.setDriverId(driver.getId());
        trip.setStatus(TripStatus.ASSIGNED);

        Trip savedTrip = tripRepository.save(trip);

        return TripMapper.toResponse(savedTrip);


    }
    public TripResponse acceptTrip(UUID tripId, String driverEmail){
        Trip trip=tripRepository.findByTripId(tripId)
                .orElseThrow(()-> new ResourceNotFoundException("Trip does not exist"));
        Driver driver=driverRepository.findByEmail(driverEmail)
                .orElseThrow(()->new ResourceNotFoundException("Driver does not exist with email "+driverEmail));
        if(!trip.getStatus().equals(TripStatus.ASSIGNED)){
            throw new IllegalStateException("Trip cannot be accepted at current state");
        }
        if(!trip.getDriverId().equals(driver.getId())){
            throw new IllegalStateException("Driver not assigned on these trip");
        }
        trip.setStatus(TripStatus.ACCEPTED);
        driver.setStatus(DriverStatus.ON_TRIP);
        tripRepository.save(trip);
        driverRepository.save(driver);
        return TripMapper.toResponse(trip);


    }
    public TripResponse startTrip(UUID tripId,String driverEmail){
        Trip trip=tripRepository.findByTripId(tripId)
                .orElseThrow(()->new ResourceNotFoundException("Trip does not exist"));
        Driver driver=driverRepository.findByEmail(driverEmail)
                .orElseThrow(()->new ResourceNotFoundException("Cannot find Driver"));
        if(!trip.getStatus().equals(TripStatus.ACCEPTED)){
            throw new IllegalStateException("Trip cannot be started at current state");
        }
        if(!trip.getDriverId().equals(driver.getId())){
            throw new IllegalStateException("driver cannot a start these trip");
        }
        trip.setStatus(TripStatus.STARTED);
        tripRepository.save(trip);
        return TripMapper.toResponse(trip);
    }
    public TripResponse completeTrip(UUID tripId,String driverEmail){
        Trip trip=tripRepository.findByTripId(tripId)
                .orElseThrow(()->new ResourceNotFoundException("Trip does not exist"));
        Driver driver=driverRepository.findByEmail(driverEmail)
                .orElseThrow(()->new ResourceNotFoundException("Cannot find Driver"));
        if(!trip.getStatus().equals(TripStatus.STARTED)){
            throw new IllegalStateException("Trip cannot be completed at current state");
        }
        if(!trip.getDriverId().equals(driver.getId())){
            throw new IllegalStateException("driver cannot complete these trip");
        }
        trip.setStatus(TripStatus.COMPLETED);
        driver.setStatus(DriverStatus.AVAILABLE);

        tripRepository.save(trip);
        driverRepository.save(driver);

        return TripMapper.toResponse(trip);
    }
    public TripResponse getTripDetail(UUID tripId) {

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalStateException("Trip not found"));

        return TripMapper.toResponse(trip);
    }

    public TripResponse cancelTrip(UUID tripId, String email){
        Trip trip=tripRepository.findByTripId(tripId)
                .orElseThrow(()->new ResourceNotFoundException("Trip does not exist"));
        User user = userRepository.findByEmail(email).orElse(null);
        Driver driver = driverRepository.findByEmail(email).orElse(null);

        if(user == null && driver == null){
            throw new ResourceNotFoundException("Invalid user/driver");
        }
        if(user != null && !trip.getUserId().equals(user.getId())){
            throw new IllegalStateException("User not authorized to cancel this trip");
        }
        if (driver!=null && trip.getDriverId() != null && !trip.getDriverId().equals(driver.getId())){
            throw new IllegalStateException("Driver not assigned on these trip");
        }
        if (trip.getStatus()==TripStatus.STARTED ||
            trip.getStatus()==TripStatus.COMPLETED){
            throw new IllegalStateException("Trip cannot be cancelled at these stage");
        }
        trip.setStatus(TripStatus.CANCELED);
        if(trip.getDriverId() != null){
            Driver assignedDriver = driverRepository.findById(trip.getDriverId())
                    .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));

            assignedDriver.setStatus(DriverStatus.AVAILABLE);
            driverRepository.save(assignedDriver);
        }
        tripRepository.save(trip);
        return TripMapper.toResponse(trip);


    }


    public Page<TripResponse> getUserTrips(Authentication authentication, int page, int size){
        User user=userRepository.findByEmail(authentication.getName())
                .orElseThrow(()->new ResourceNotFoundException("User not found"));
        Pageable pageable = PageRequest.of(page, size);
        Page<Trip> tripPage = tripRepository.findByUserId(user.getId(), pageable);
        return tripPage.map(TripMapper::toResponse);
    }



    public Page<TripResponse> getDriverTrips(Authentication authentication,int page, int size){
        Driver driver =driverRepository.findByEmail(authentication.getName())
                .orElseThrow(()->new ResourceNotFoundException("Driver not found"));
        Pageable pageable = PageRequest.of(page, size);
        Page<Trip> tripPage = tripRepository.findByDriverId(driver.getId(), pageable);
        return tripPage.map(TripMapper::toResponse);

    }
    private void setFareAndDistance(Trip trip){

        Double dist= DistanceUtil.calculateDistance(trip.getPickupLocation(),trip.getDropLocation());
        Double fare=30 +(10*dist);//base fare + rate perkm*dist

        trip.setDistance(dist);
        trip.setFare(fare);




    }

    private Driver findNearestDriver(Trip trip){
        List<Driver> drivers=driverRepository.findByStatus(DriverStatus.AVAILABLE);

        Driver nearestDriver=null;
        double minDistance = Double.MAX_VALUE;;

        for(Driver driver : drivers){
            if(driver.getCurrentLocation()==null)continue;
            double distance = DistanceUtil.calculateDistance(
                    driver.getCurrentLocation(),
                    trip.getPickupLocation()
            );
            if(distance<minDistance){
                minDistance=distance;
                nearestDriver=driver;
            }

        }
        return nearestDriver;

    }

    public void assignNextDriver(UUID tripId){

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip does not exist"));

        if (trip.getStatus() != TripStatus.ASSIGNED &&
                trip.getStatus() != TripStatus.CREATED) {
            return;
        }

        List<Driver> drivers = driverRepository.findByStatus(DriverStatus.AVAILABLE);

        Driver selectedDriver = null;
        double minDistance = Double.MAX_VALUE;

        for (Driver driver : drivers) {

            if (trip.getTriedDriverIds().contains(driver.getId())) continue;
            if (driver.getCurrentLocation() == null) continue;

            double distance = DistanceUtil.calculateDistance(
                    driver.getCurrentLocation(),
                    trip.getPickupLocation()
            );

            if (distance < minDistance) {
                minDistance = distance;
                selectedDriver = driver;
            }
        }

        if (selectedDriver == null) {
            trip.setStatus(TripStatus.CANCELED);
            tripRepository.save(trip);
            return;
        }

        if (selectedDriver.getStatus() != DriverStatus.AVAILABLE) {
            assignNextDriver(tripId);
            return;
        }

        trip.setDriverId(selectedDriver.getId());
        trip.setStatus(TripStatus.ASSIGNED);
        trip.getTriedDriverIds().add(selectedDriver.getId());

        selectedDriver.setStatus(DriverStatus.BUSY);

        tripRepository.save(trip);
        driverRepository.save(selectedDriver);
    }



    public void rejectTrip(UUID tripId, String driverEmail) {

        Trip trip = tripRepository.findByTripId(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found"));

        Driver driver = driverRepository.findByEmail(driverEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));

        if (!trip.getDriverId().equals(driver.getId())) {
            throw new IllegalStateException("Driver not assigned");
        }

        // reset driver
        driver.setStatus(DriverStatus.AVAILABLE);
        driverRepository.save(driver);

        // remove driver
        trip.setDriverId(null);
        tripRepository.save(trip);

        // retry
        assignNextDriver(tripId);
    }


















}




























