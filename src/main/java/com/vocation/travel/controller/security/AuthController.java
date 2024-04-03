package com.vocation.travel.controller.security;

import com.vocation.travel.common.constant.ApiConstant;
import com.vocation.travel.common.constant.CodeConstant;
import com.vocation.travel.dto.AuthDTO.LoginRequest;
import com.vocation.travel.dto.UsersDTO;
import com.vocation.travel.model.BaseResponse;
import com.vocation.travel.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstant.API_AUTH)
public class AuthController {
  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping(ApiConstant.API_LOGIN)
  public ResponseEntity<?> login(@RequestBody LoginRequest userLogin) {
    return ResponseEntity.ok(authenticationService.login(userLogin));
  }

  @PostMapping(ApiConstant.API_REGISTER)
  public ResponseEntity<?> register(@RequestBody UsersDTO usersDto) {
    return ResponseEntity.ok(authenticationService.register(usersDto));
  }

  @PostMapping(ApiConstant.API_AUTHENTICATION)
  public ResponseEntity<?> authorization() {
    return ResponseEntity.ok(Boolean.TRUE);
  }
}
