package com.vocation.travel.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * Table Users.
 *
 * @author Minh An
 * */
@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "user_name"),
    @UniqueConstraint(columnNames = "email")
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
  private String password;

  @NonNull
  private String role;

  private String rememberPassword;

  @OneToMany(mappedBy = "users")
  private List<Friend> friends;
}
