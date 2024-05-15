package com.vocation.travel.controller;

import com.vocation.travel.common.constant.ApiConstant;
import com.vocation.travel.dto.TripDTO;
import com.vocation.travel.dto.UsersDTO;
import com.vocation.travel.entity.User;
import com.vocation.travel.model.BaseResponse;
import com.vocation.travel.service.CRUD;
import com.vocation.travel.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping(ApiConstant.API_USER)
public class UserController {
    @Autowired
    private CRUD<UsersDTO, BaseResponse> userService;

    @PostMapping(ApiConstant.API_UPDATE)
    public ResponseEntity<?> update(@RequestBody UsersDTO usersDTO) {
        return ResponseEntity.ok(userService.update(usersDTO));
    }
}