package com.vocation.travel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "user_name"),
    @UniqueConstraint(columnNames = "email")
})
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(unique = true)
  private String userId;

  @Column(name = "user_name", unique = true)
  @NonNull
  private String username;

  @NonNull
  @Column(name = "email", unique = true)
  private String email;

  @NonNull
  @JsonIgnore
  private String password;
}
