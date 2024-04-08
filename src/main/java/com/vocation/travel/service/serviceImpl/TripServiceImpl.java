package com.vocation.travel.service.serviceImpl;

import com.vocation.travel.common.Log;
import com.vocation.travel.config.ExceptionHandler.*;
import com.vocation.travel.config.Message;
import com.vocation.travel.dto.TripDTO;
import com.vocation.travel.entity.Trip;
import com.vocation.travel.model.BaseResponse;
import com.vocation.travel.repository.TripRepository;
import com.vocation.travel.service.CRUD;
import com.vocation.travel.util.DateTimeUtils;
import com.vocation.travel.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.vocation.travel.common.constant.CodeConstant.RESPONSE_SUCCESS;

/**
 * Trip service.
 *
 * @author Minh An
 * @version ver0.0.1
 * */
@Service
public class TripServiceImpl extends Message implements CRUD<TripDTO, Trip> {
    @Autowired
    private TripRepository tripRepository;

    private final static String SERVICE_NAME = "TripService";

    /**
     * Create.
     *
     * @param request TripDTO
     * @return BaseResponse
     * */
    @Override
    public BaseResponse create(TripDTO request) {
        final String METHOD_NAME = "create";
        Log.startLog(SERVICE_NAME, METHOD_NAME);
        Log.inputLog(request);
        Trip trip = convertEntity(request, METHOD_NAME);
        BaseResponse response;
        if (Utils.isNull(trip)) {
            response = new BaseResponse(RESPONSE_SUCCESS, Boolean.FALSE, getMessage("CrateFail"));
            Log.outputLog(response);
            Log.endLog(SERVICE_NAME, METHOD_NAME);
            return response;
        }
        trip.setStartDate(request.getStartDate());
        trip.setEndDate(request.getEndDate());
        trip.setCreateBy(Utils.userSystem().getUsername());
        trip.setCreateDate(LocalDateTime.now());
        trip.setUpdateBy(Utils.userSystem().getUsername());
        trip.setUpdateDate(LocalDateTime.now());

        tripRepository.save(trip);
        response = new BaseResponse(RESPONSE_SUCCESS, Boolean.TRUE, getMessage("CrateSuccess"));
        Log.outputLog(response);
        Log.endLog(SERVICE_NAME, METHOD_NAME);
        return response;
    }

    /**
     * Read.
     *
     * @param request TripDTO
     * @return BaseResponse
     * */
    @Override
    public BaseResponse read(TripDTO request) {
        final String METHOD_NAME = "read";
        Log.startLog(SERVICE_NAME, METHOD_NAME);
        Log.inputLog(request);
        Trip trip = convertEntity(request, METHOD_NAME);
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

    /**
     * Update.
     *
     * @param request TripDTO
     * @return BaseResponse
     * */
    @Override
    public BaseResponse update(TripDTO request) {
        final String METHOD_NAME = "update";
        Log.startLog(SERVICE_NAME, METHOD_NAME);
        Log.inputLog(request);
        Trip trip = convertEntity(request, METHOD_NAME);
        BaseResponse response;
        if (Utils.isNull(trip)) {
            response = new BaseResponse(RESPONSE_SUCCESS, Boolean.FALSE, getMessage("UpdateFail"));
            Log.outputLog(response);
            Log.endLog(SERVICE_NAME, METHOD_NAME);
            return response;
        }
        trip.setUpdateDate(LocalDateTime.now());
        tripRepository.save(trip);
        response = new BaseResponse(RESPONSE_SUCCESS, Boolean.TRUE, getMessage("UpdateSuccess"));
        Log.outputLog(response);
        Log.endLog(SERVICE_NAME, METHOD_NAME);
        return response;
    }

    /**
     * Delete.
     *
     * @param request TripDTO
     * @return BaseResponse
     * */
    @Override
    public BaseResponse delete(TripDTO request) {
        final String METHOD_NAME = "delete";
        Log.startLog(SERVICE_NAME, METHOD_NAME);
        Log.inputLog(request);
        Trip trip = convertEntity(request, METHOD_NAME);
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

    /**
     * Convert entity.
     *
     * @param method String
     * @param request TripDTO
     * @return Trip
     * */
    private Trip convertEntity(TripDTO request, String method) {
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

    /**
     * Check input params.
     *
     * @param request TripDTO
     * @return boolean
     * */
    private boolean checkInputParams(TripDTO request) {
        return !Utils.isEmpty(request.getId()) && !Utils.isEmpty(request.getTitle())
                && !Utils.isEmpty(request.getIdProvince()) && !Utils.isNull(request.getStartDate())
                && !Utils.isNull(request.getEndDate());
    }

    /**
     * Validate time.
     *
     *
     * */
    private void validateTime(LocalDateTime startDate, LocalDateTime endDate, BaseResponse response, String method) {
        if (DateTimeUtils.checkFinishTimeBeforeStartTime(startDate, endDate)) {
            Log.outputLog(response);
            Log.endLog(SERVICE_NAME, method);
            throw new SystemErrorException(getMessage("DateStartFail", new Object[] {startDate, endDate}));
        }

        if (DateTimeUtils.checkFinishTimeBeforeStartTime(startDate, endDate)) {
            Log.outputLog(response);
            Log.endLog(SERVICE_NAME, method);
            throw new BadRequestException(getMessage("DateEndFail", new Object[] {startDate, endDate}));
        }
    }
}
