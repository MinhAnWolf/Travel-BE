package com.vocation.travel.service.serviceImpl;

import com.vocation.travel.common.Log;
import com.vocation.travel.config.ExceptionHandler.*;
import com.vocation.travel.config.Message;
import com.vocation.travel.dto.MemberDTO;
import com.vocation.travel.dto.TripDTO;
import com.vocation.travel.entity.Trip;
import com.vocation.travel.model.BaseResponse;
import com.vocation.travel.repository.TripRepository;
import com.vocation.travel.service.CRUD;
import com.vocation.travel.util.DateTimeUtils;
import com.vocation.travel.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.Date;

import static com.vocation.travel.common.constant.CodeConstant.RESPONSE_SUCCESS;

/**
 * Trip service.
 *
 * @author Minh An
 * @version ver0.0.1
 * */
@Service
public class TripServiceImpl extends Message implements CRUD<TripDTO, BaseResponse> {
    @Autowired
    private TripRepository tripRepository;

    private final static String SERVICE_NAME = "TripService";

    @Autowired
    private CRUD<MemberDTO, BaseResponse> memberService;

    /**
     * Create trip.
     *
     * @param request TripDTO
     * @return BaseResponse
     * */
    @Override
    public BaseResponse create(TripDTO request) {
        final String METHOD_NAME = "create";
        try {
            Log.startLog(SERVICE_NAME, METHOD_NAME);
            Log.inputLog(request);
            validateTime(request.getStartDate(), request.getEndDate(), request, METHOD_NAME);
            Trip trip = convertEntity(request, METHOD_NAME);
            BaseResponse response;
            trip.setCreateBy(Utils.userSystem());
            trip.setUpdateBy(Utils.userSystem());
            tripRepository.save(trip);
            response = new BaseResponse(RESPONSE_SUCCESS, Boolean.TRUE, getMessage("CrateSuccess"));
            Log.outputLog(response);
            Log.endLog(SERVICE_NAME, METHOD_NAME);
            return response;
        } catch (Exception e) {
            Log.outputLog(request);
            Log.endLog(SERVICE_NAME, METHOD_NAME);
            throw new SystemErrorException(getMessage("CrateFail"));
        }
    }

    /**
     * Read trip.
     *
     * @param request TripDTO
     * @return BaseResponse
     * */
    @Override
    public BaseResponse read(TripDTO request) {
        final String METHOD_NAME = "read";
        try {
            Log.startLog(SERVICE_NAME, METHOD_NAME);
            Log.inputLog(request);
            BaseResponse response;
            response = new BaseResponse(RESPONSE_SUCCESS, tripRepository.findAll(), getMessage("ReadSuccess"));
            Log.outputLog(response);
            Log.endLog(SERVICE_NAME, METHOD_NAME);
            return response;
        } catch (Exception e) {
            Log.outputLog(request);
            Log.endLog(SERVICE_NAME, METHOD_NAME);
            throw new SystemErrorException(getMessage("ReadFail"));
        }
    }

    /**
     * Update trip.
     *
     * @param request TripDTO
     * @return BaseResponse
     * */
    @Override
    public BaseResponse update(TripDTO request) {
        final String METHOD_NAME = "update";
        try {
            Log.startLog(SERVICE_NAME, METHOD_NAME);
            Log.inputLog(request);
            validateTime(request.getStartDate(), request.getEndDate(), request, METHOD_NAME);
            Trip trip = convertEntity(request, METHOD_NAME);
            BaseResponse response;
            trip.setUpdateBy(Utils.userSystem());
            tripRepository.save(trip);
            response = new BaseResponse(RESPONSE_SUCCESS, Boolean.TRUE, getMessage("UpdateSuccess"));
            Log.outputLog(response);
            Log.endLog(SERVICE_NAME, METHOD_NAME);
            return response;
        } catch (Exception e) {
            Log.outputLog(request);
            Log.endLog(SERVICE_NAME, METHOD_NAME);
            throw new SystemErrorException(getMessage("UpdateFail"));
        }
    }

    /**
     * Delete trip.
     *
     * @param request TripDTO
     * @return BaseResponse
     * */
    @Override
    public BaseResponse delete(TripDTO request) {
        final String METHOD_NAME = "delete";
        try {
            Log.startLog(SERVICE_NAME, METHOD_NAME);
            Log.inputLog(request);
            Trip trip = convertEntity(request, METHOD_NAME);
            BaseResponse response;
            tripRepository.delete(trip);
            response = new BaseResponse(RESPONSE_SUCCESS, Boolean.TRUE, getMessage("DeleteSuccess"));
            Log.outputLog(response);
            Log.endLog(SERVICE_NAME, METHOD_NAME);
            return response;
        } catch (Exception e) {
            Log.outputLog(request);
            Log.endLog(SERVICE_NAME, METHOD_NAME);
            throw new SystemErrorException(getMessage("DeleteFail"));
        }

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
                throw new BadRequestException(getMessage("ParamsNull"));
            }
        }
        trip.setImage(request.getImage());
        trip.setDescription(request.getDescription());
        trip.setTitle(request.getTitle());
        trip.setStartDate(request.getStartDate());
        trip.setEndDate(request.getEndDate());
        trip.setAddress(request.getAddress());
        if (!request.getMembers().isEmpty()) {

        }

        trip.setMembers(request.getMembers());
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
                && !Utils.isEmpty(request.getAddress()) && !Utils.isNull(request.getStartDate())
                && !Utils.isNull(request.getEndDate());
    }

    /**
     * Validate time.
     *
     * @param startDate Date
     * @param endDate Date
     * @param request TripDTO
     * @param method String
     * */
    private void validateTime(Date startDate, Date endDate, TripDTO request, String method) {
        if (DateTimeUtils.checkStartTimeAfterFinishTime(startDate, endDate)) {
            Log.outputLog(request);
            Log.endLog(SERVICE_NAME, method);
            throw new SystemErrorException(getMessage("DateStartFail", new Object[] {startDate, endDate}));
        }

        if (DateTimeUtils.checkFinishTimeBeforeStartTime(startDate, endDate)) {
            Log.outputLog(request);
            Log.endLog(SERVICE_NAME, method);
            throw new BadRequestException(getMessage("DateEndFail", new Object[] {startDate, endDate}));
        }
    }
}
