package com.vocation.travel.repository;

import com.vocation.travel.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, String> {

}
