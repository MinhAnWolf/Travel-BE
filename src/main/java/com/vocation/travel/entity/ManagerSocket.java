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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "MANAGER_SOCKET")
public class ManagerSocket {
  @GeneratedValue(strategy = GenerationType.UUID)
  @Id
  @Column(name = "ID")
  private String id;
  private String userId;
  private String sessionId;
}
