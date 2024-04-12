package com.vocation.travel.controller;

import com.vocation.travel.common.constant.ApiConstant;
import com.vocation.travel.dto.InformationDTO;
import com.vocation.travel.entity.Information;
import com.vocation.travel.service.CRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstant.API_INFORMATION)
public class InformationController {

    @Autowired
    private CRUD<InformationDTO, Information> informationService;

    @PostMapping(ApiConstant.API_CREATE)
    public ResponseEntity<?> create(@RequestBody InformationDTO informationDTO){
        return ResponseEntity.ok(informationService.create(informationDTO));
    }
}
