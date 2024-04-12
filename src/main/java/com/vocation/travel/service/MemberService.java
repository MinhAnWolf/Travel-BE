package com.vocation.travel.service;

import com.vocation.travel.dto.MemberDTO;
import java.util.List;

public interface MemberService {
  List<MemberDTO> getMemberByTravelId(String idMember, String idTravel);
  boolean checkUserInTravel(String userId, String idTravel);
}
