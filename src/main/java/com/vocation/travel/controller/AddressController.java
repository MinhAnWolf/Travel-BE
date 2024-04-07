package com.vocation.travel.controller;

import com.vocation.travel.common.constant.ApiConstant;
import com.vocation.travel.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstant.API_ADDRESS)
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping(ApiConstant.API_DISTRICT)
    public ResponseEntity<?> getDistrict() {
        return ResponseEntity.ok(addressService.listDistrict());
    }

    @GetMapping(ApiConstant.API_PROVINCE)
    public ResponseEntity<?> getProvince() {
        return ResponseEntity.ok(addressService.listProvince());
    }

    @GetMapping(ApiConstant.API_WARD)
    public ResponseEntity<?> getWard() {
        return ResponseEntity.ok(addressService.listWard());
    }
}
