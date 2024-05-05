package com.vocation.travel.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

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
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
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
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String password;

  @NonNull
  private String role;

  private String rememberPassword;

  @Column(name = "FULL_NAME")
  private String infoName;

  @Column(name = "BIRTHDAY")
  private Date infoBirthday;

  @Column(name = "GENDER")
  private boolean infoGender;

  @Column(name = "PHONE")
  private String infoPhone;

  @Column(name = "AVATAR")
  private String avatar;

  @Column(name = "PROVINCE_ID")
  private String provinceId;

  @OneToMany(mappedBy = "user")
  private List<Friend> friends;
}
