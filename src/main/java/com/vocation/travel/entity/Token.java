package com.vocation.travel.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "TOKEN")
public class Token {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(unique = true)
  private String id;
  private String access;
  private String refresh;

  public Token(String id) {
    this.id = id;
  }
}
