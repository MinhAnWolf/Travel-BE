package com.vocation.travel.repository;

import com.vocation.travel.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TripRepository extends JpaRepository<Trip, String> {
    @Query(value = "SELECT owner FROM TRIP WHERE id = :idTrip", nativeQuery = true)
    String getOwnerTrip(String idTrip);

}


