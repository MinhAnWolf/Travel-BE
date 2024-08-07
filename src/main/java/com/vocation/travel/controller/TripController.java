package com.vocation.travel.controller;

import com.vocation.travel.common.constant.ApiConstant;
import com.vocation.travel.dto.TripDTO;
import com.vocation.travel.entity.Trip;
import com.vocation.travel.model.BaseResponse;
import com.vocation.travel.service.CRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstant.API_TRIP)
public class TripController {
    @Autowired
    private CRUD<TripDTO, BaseResponse> tripService;

    @PostMapping(ApiConstant.API_CREATE)
    public ResponseEntity<?> create(@RequestBody TripDTO tripDto) {
        return ResponseEntity.ok(tripService.create(tripDto));
    }

    @GetMapping(ApiConstant.API_READ)
    public ResponseEntity<?> read(@RequestBody TripDTO tripDto) {
        return ResponseEntity.ok(tripService.read(tripDto));
    }

    @PostMapping(ApiConstant.API_UPDATE)
    public ResponseEntity<?> update(@RequestBody TripDTO tripDto) {
        return ResponseEntity.ok(tripService.update(tripDto));
    }

    @PostMapping(ApiConstant.API_DELETE)
    public ResponseEntity<?> delete(@RequestBody TripDTO tripDto) {
        return ResponseEntity.ok(tripService.delete(tripDto));
    }
}
