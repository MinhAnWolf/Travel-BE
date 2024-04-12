package com.vocation.travel.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Getter
@Setter
@Entity(name = "MEMBER")
public class Member extends HelperBy {
  @GeneratedValue(strategy = GenerationType.UUID)
  @Id
  @Column(name = "ID")
  private String id;

  @Column(name = "ID_USER")
  private String idUser;

  @Column(name = "ROLE")
  private String role;

  @ManyToOne
  @JoinColumn(name = "ID_TRIP")
  private Trip trip;
}
