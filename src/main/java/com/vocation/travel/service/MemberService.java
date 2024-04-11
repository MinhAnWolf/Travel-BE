package com.vocation.travel.service;

import com.vocation.travel.dto.MemberDTO;
import java.util.List;

public interface MemberService {
  List<MemberDTO> getMemberByTravelDetailId(String idTravelDetail);
}
