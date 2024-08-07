package com.vocation.travel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vocation.travel.entity.User;
import lombok.*;

import java.util.Date;

/**
 * UserDTO.
 *
 * @author Minh An
 * */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsersDTO {
  private String id;
  private String username;
  private String fullName;
  private String email;
  private Date birthday;
  private Boolean gender;
  private String phone;
  private String avatar;
  private String password;

  /**
   * UserDto convert to User for all property.
   *
   * @param usersDto UsersDTO
   * @return User
   * */
  public static User UserDtoConvertToUser(UsersDTO usersDto) {
    User user = new User();
    user.setUserId(usersDto.getId());
    user.setInfoName(usersDto.getFullName());
    user.setAvatar(usersDto.getAvatar());
    user.setUsername(usersDto.getUsername());
    user.setEmail(usersDto.getEmail());
    user.setPassword(usersDto.getPassword());
    return user;
  }
}