package com.vocation.travel.controller;

import com.vocation.travel.common.constant.ApiConstant;
import com.vocation.travel.dto.TripDTO;
import com.vocation.travel.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstant.API_MEMBER)
public class MemberController {
  @Autowired
  private MemberService memberService;

  @GetMapping(ApiConstant.API_READ)
  public ResponseEntity<?> read(@RequestParam("id-member") String idMember, @RequestParam("id-travel") String idTravel) {
    return ResponseEntity.ok(memberService.getMemberByTravelId(idMember, idTravel));
  }
}
