package com.vocation.travel.service.serviceImpl;

import com.vocation.travel.common.Log;
import com.vocation.travel.common.constant.CommonConstant;
import com.vocation.travel.config.ExceptionHandler.*;
import com.vocation.travel.config.Message;
import com.vocation.travel.dto.MemberDTO;
import com.vocation.travel.dto.TripDTO;
import com.vocation.travel.entity.Member;
import com.vocation.travel.entity.Trip;
import com.vocation.travel.model.BaseResponse;
import com.vocation.travel.repository.TripRepository;
import com.vocation.travel.service.CRUD;
import com.vocation.travel.util.DateTimeUtils;
import com.vocation.travel.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

import static com.vocation.travel.common.constant.CommonConstant.RESPONSE_SUCCESS;

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
        try {
            Log.startLog(SERVICE_NAME, CommonConstant.METHOD_CREATE);
            Log.inputLog(request);
            checkInputParams(request, CommonConstant.METHOD_CREATE);
            validateTime(request.getStartDate(), request.getEndDate(), request, CommonConstant.METHOD_CREATE);
            Trip trip = convertEntity(request, CommonConstant.METHOD_CREATE);
            BaseResponse response;
            trip.setCreateBy(Utils.userSystem());
            trip.setUpdateBy(Utils.userSystem());
            tripRepository.save(trip);
            if (!request.getMembers().isEmpty()) {
                for (Member member: request.getMembers()) {
                    MemberDTO memberDto = new MemberDTO();
                    memberDto.setTrip(trip);
                    memberDto.setRole(member.getRole());
                    memberDto.setIdUser(member.getIdUser());
                    memberService.create(memberDto);
                }
            }
            response = new BaseResponse(RESPONSE_SUCCESS, Boolean.TRUE, getMessage("CrateSuccess"));
            Log.outputLog(response);
            Log.endLog(SERVICE_NAME, CommonConstant.METHOD_CREATE);
            return response;
        } catch (Exception e) {
            Log.outputLog(request);
            Log.endLog(SERVICE_NAME, CommonConstant.METHOD_CREATE);
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
        if (method.equals(method)) {
            trip.setId(null);
        } else {
            if (!checkInputParams(request, method)) {
                throw new BadRequestException(getMessage("ParamsNull"));
            }
        }
        trip.setImage(request.getImage());
        trip.setDescription(request.getDescription());
        trip.setTitle(request.getTitle());
        trip.setStartDate(request.getStartDate());
        trip.setEndDate(request.getEndDate());
        trip.setAddress(request.getAddress());
        trip.setMembers(request.getMembers());
        trip.setOwner(request.getOwner());
        return trip;
    }

    /**
     * Check input params.
     *
     * @param request TripDTO
     * @return boolean
     * */
    private boolean checkInputParams(TripDTO request, String method) {
        if (method.equals(CommonConstant.METHOD_CREATE)) {
            return !Utils.isEmpty(request.getTitle())
                || !Utils.isEmpty(request.getAddress()) || !Utils.isNull(request.getStartDate())
                || !Utils.isNull(request.getEndDate());
        }
        return !Utils.isEmpty(request.getId()) || !Utils.isEmpty(request.getTitle())
            || !Utils.isEmpty(request.getAddress()) || !Utils.isNull(request.getStartDate())
            || !Utils.isNull(request.getEndDate());
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
