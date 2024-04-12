package com.vocation.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * UserDTO.
 *
 * @author Minh An
 * */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {
  private String username;
  private String email;
  private String password;
}
