package com.vocation.travel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "TRIP_DETAIL")
@Getter
@Setter
public class TripDetail extends HelperBy{
  @GeneratedValue(strategy = GenerationType.UUID)
  @Id
  @Column(name = "ID")
  private String id;

  @OneToOne(mappedBy = "tripDetail")
  private Trip trip;
}
