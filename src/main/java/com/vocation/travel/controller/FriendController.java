package com.vocation.travel.controller;

import com.vocation.travel.common.constant.ApiConstant;
import com.vocation.travel.dto.FriendDTO;
import com.vocation.travel.model.BaseResponse;
import com.vocation.travel.service.CRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstant.API_ADDRESS)
public class FriendController {
  @Autowired
  private CRUD<FriendDTO, BaseResponse> friendService;

  @PostMapping(ApiConstant.API_ADD)
  public ResponseEntity<?> add(@RequestBody FriendDTO friendDTO) {
    return ResponseEntity.ok(friendService.create(friendDTO));
  }

  @GetMapping(ApiConstant.API_GET)
  public ResponseEntity<?> get() {
    return ResponseEntity.ok(friendService.read(null));
  }

  @PostMapping(ApiConstant.API_UPDATE)
  public ResponseEntity<?> update(@RequestBody FriendDTO friendDTO) {
    return ResponseEntity.ok(friendService.update(friendDTO));
  }

  @PostMapping(ApiConstant.API_DELETE)
  public ResponseEntity<?> delete(@RequestBody FriendDTO friendDTO) {
    return ResponseEntity.ok(friendService.delete(friendDTO));
  }
}
