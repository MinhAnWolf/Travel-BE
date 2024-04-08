package com.vocation.travel.service.serviceImpl;

import com.vocation.travel.common.Log;
import com.vocation.travel.config.Message;
import com.vocation.travel.dto.TripDTO;
import com.vocation.travel.entity.Trip;
import com.vocation.travel.model.BaseResponse;
import com.vocation.travel.repository.TripRepository;
import com.vocation.travel.service.CRUD;
import com.vocation.travel.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.vocation.travel.common.constant.CodeConstant.RESPONSE_SUCCESS;

@Service
public class TripImpl extends Message implements CRUD<TripDTO, Trip> {
    @Autowired
    private TripRepository tripRepository;

    private final static String SERVICE_NAME = "TripService";

    @Override
    public BaseResponse create(TripDTO request) {
        final String METHOD_NAME = "create";
        Trip trip = handleStartMethod(request, METHOD_NAME);
        BaseResponse response;
        if (Utils.isNull(trip)) {
            response = new BaseResponse(RESPONSE_SUCCESS, Boolean.FALSE, getMessage("CrateFail"));
            Log.outputLog(response);
            Log.endLog(SERVICE_NAME, METHOD_NAME);
            return response;
        }
        trip.setStartDate(LocalDateTime.now());
        trip.setEndDate(LocalDateTime.now());
        tripRepository.save(trip);
        response = new BaseResponse(RESPONSE_SUCCESS, Boolean.TRUE, getMessage("CrateSuccess"));
        Log.outputLog(response);
        Log.endLog(SERVICE_NAME, METHOD_NAME);
        return response;
    }

    @Override
    public BaseResponse read(TripDTO request) {
        final String METHOD_NAME = "read";
        Trip trip = handleStartMethod(request, METHOD_NAME);
        BaseResponse response;
        if (Utils.isNull(trip)) {
            response = new BaseResponse(RESPONSE_SUCCESS, Boolean.FALSE, getMessage("ReadFail"));
            Log.outputLog(response);
            Log.endLog(SERVICE_NAME, METHOD_NAME);
            return response;
        }
        response = new BaseResponse(RESPONSE_SUCCESS, tripRepository.findAll(), getMessage("ReadSuccess"));
        Log.outputLog(response);
        Log.endLog(SERVICE_NAME, METHOD_NAME);
        return response;
    }

    @Override
    public BaseResponse update(TripDTO request) {
        final String METHOD_NAME = "update";
        Trip trip = handleStartMethod(request, METHOD_NAME);
        BaseResponse response;
        if (Utils.isNull(trip)) {
            response = new BaseResponse(RESPONSE_SUCCESS, Boolean.FALSE, getMessage("UpdateFail"));
            Log.outputLog(response);
            Log.endLog(SERVICE_NAME, METHOD_NAME);
            return response;
        }
        tripRepository.save(trip);
        response = new BaseResponse(RESPONSE_SUCCESS, Boolean.TRUE, getMessage("UpdateSuccess"));
        Log.outputLog(response);
        Log.endLog(SERVICE_NAME, METHOD_NAME);
        return response;
    }

    @Override
    public BaseResponse delete(TripDTO request) {
        final String METHOD_NAME = "delete";
        Trip trip = handleStartMethod(request, METHOD_NAME);
        BaseResponse response;
        if (Utils.isNull(trip)) {
            response = new BaseResponse(RESPONSE_SUCCESS, Boolean.FALSE, getMessage("DeleteFail"));
            Log.outputLog(response);
            Log.endLog(SERVICE_NAME, METHOD_NAME);
            return response;
        }
        tripRepository.delete(trip);
        response = new BaseResponse(RESPONSE_SUCCESS, Boolean.TRUE, getMessage("DeleteSuccess"));
        Log.outputLog(response);
        Log.endLog(SERVICE_NAME, METHOD_NAME);
        return response;
    }

    @Override
    public Trip handleStartMethod(TripDTO request, String method) {
        Log.startLog(SERVICE_NAME, method);
        Log.inputLog(request);
        Trip trip = new Trip();
        if (method.equals("create")) {
            trip.setId(null);
        } else {
            if (!checkInputParams(request)) {
                return null;
            }
        }
        trip.setTitle(request.getTitle());
        trip.setStartDate(request.getStartDate());
        trip.setEndDate(request.getEndDate());
        trip.setIdProvince(request.getIdProvince());
        return trip;
    }

    private boolean checkInputParams(TripDTO request) {
        return !Utils.isEmpty(request.getId()) && !Utils.isEmpty(request.getTitle())
                && !Utils.isEmpty(request.getIdProvince()) && !Utils.isNull(request.getStartDate())
                && !Utils.isNull(request.getEndDate());
    }
}
