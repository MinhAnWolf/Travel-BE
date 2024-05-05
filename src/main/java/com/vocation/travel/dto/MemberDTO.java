package com.vocation.travel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vocation.travel.entity.Trip;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberDTO {
  private String id;
  private String idUser;
  private String idTrip;
  private Trip trip;
  private String role;
  private UsersDTO usersDTO;
}
