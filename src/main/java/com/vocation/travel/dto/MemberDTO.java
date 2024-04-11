package com.vocation.travel.dto;

import com.vocation.travel.entity.Trip;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemberDTO {
  private String id;
  private String idUser;
  private String idTrip;
  private Trip trip;
  private String role;
  private InformationDTO informationDTO;
}
