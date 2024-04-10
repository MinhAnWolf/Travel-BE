package com.vocation.travel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity(name = "TRIP_DETAIL")
public class TripDetail extends HelperBy{
  @GeneratedValue(strategy = GenerationType.UUID)
  @Id
  @Column(name = "ID")
  private String id;

  @OneToOne(mappedBy = "tripDetail")
  private Trip trip;
}
