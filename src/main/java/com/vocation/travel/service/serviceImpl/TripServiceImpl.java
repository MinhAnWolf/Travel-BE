package com.vocation.travel.service.serviceImpl;

import com.vocation.travel.common.Log;
import com.vocation.travel.common.constant.CommonConstant;
import com.vocation.travel.config.ExceptionHandler.*;
import com.vocation.travel.config.Message;
import com.vocation.travel.dto.MemberDTO;
import com.vocation.travel.dto.TripDTO;
import com.vocation.travel.entity.Image;
import com.vocation.travel.entity.Member;
import com.vocation.travel.entity.Trip;
import com.vocation.travel.model.BaseResponse;
import com.vocation.travel.repository.TripRepository;
import com.vocation.travel.service.CRUD;
import com.vocation.travel.service.MemberService;
import com.vocation.travel.service.UserService;
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

    @Autowired
    private ImageServiceImpl imageService;

    @Autowired
    private MemberService memberServices;

    @Autowired
    private UserService userService;

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

            //validate trip
            checkInputParams(request, CommonConstant.METHOD_CREATE);
            validateTime(request.getStartDate(), request.getEndDate(), request, CommonConstant.METHOD_CREATE);

            // register trip
            Trip trip = convertEntity(request, CommonConstant.METHOD_CREATE);
            trip.setCreateBy(Utils.userSystem());
            trip.setUpdateBy(Utils.userSystem());
            tripRepository.save(trip);

            // register image
            if (!Utils.objNull(request.getLinkImages())) {
                saveImage(request, trip);
            }

            // register member
            if (!Utils.objNull(request.getMembers())) {
                if (!request.getMembers().isEmpty()) {
                    for (Member member: request.getMembers()) {
                        MemberDTO memberDto = new MemberDTO();
                        memberDto.setTrip(trip);
                        memberDto.setRole(CommonConstant.RoleTrip.MEMBER);
                        memberDto.setIdUser(member.getIdUser());
                        if (member.getIdUser().equals(userService.getUserByUserName().getUserId())) {
                            memberDto.setRole(CommonConstant.RoleTrip.OWNER);
                        }
                        memberService.create(memberDto);
                    }
                }
            }

            BaseResponse response = new BaseResponse(RESPONSE_SUCCESS, Boolean.TRUE, getMessage("CreateSuccess"));
            Log.outputLog(response);
            Log.endLog(SERVICE_NAME, CommonConstant.METHOD_CREATE);
            return response;
        } catch (Exception e) {
            Log.errorLog(e);
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
        try {
            Log.startLog(SERVICE_NAME, CommonConstant.METHOD_READ);
            Log.inputLog(request);
            checkPermissionsTravel(request);
            BaseResponse response = new BaseResponse(RESPONSE_SUCCESS, tripRepository.findAll(), getMessage("ReadSuccess"));
            Log.outputLog(response);
            Log.endLog(SERVICE_NAME, CommonConstant.METHOD_READ);
            return response;
        } catch (Exception e) {
            Log.errorLog(e);
            Log.endLog(SERVICE_NAME, CommonConstant.METHOD_READ);
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
        try {
            Log.startLog(SERVICE_NAME, CommonConstant.METHOD_UPDATE);
            Log.inputLog(request);
            checkPermissionsTravel(request);
            validateTime(request.getStartDate(), request.getEndDate(), request, CommonConstant.METHOD_UPDATE);
            Trip trip = convertEntity(request, CommonConstant.METHOD_UPDATE);
            BaseResponse response;
            trip.setUpdateBy(Utils.userSystem());
            tripRepository.save(trip);
            response = new BaseResponse(RESPONSE_SUCCESS, Boolean.TRUE, getMessage("UpdateSuccess"));
            Log.outputLog(response);
            Log.endLog(SERVICE_NAME, CommonConstant.METHOD_UPDATE);
            return response;
        } catch (Exception e) {
            Log.errorLog(e);
            Log.endLog(SERVICE_NAME, CommonConstant.METHOD_UPDATE);
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
        try {
            Log.startLog(SERVICE_NAME, CommonConstant.METHOD_DELETE);
            Log.inputLog(request);
            String owner = tripRepository.getOwnerTrip(request.getId());
            if (!userService.getUserByUserName().getUserId().equals(owner)) {
                Log.outputLog(request);
                Log.endLog(SERVICE_NAME, CommonConstant.METHOD_DELETE);
                throw new SystemErrorException(getMessage("Permissions"));
            }
            tripRepository.delete(convertEntity(request, CommonConstant.METHOD_DELETE));
            BaseResponse response = new BaseResponse(RESPONSE_SUCCESS, Boolean.TRUE, getMessage("DeleteSuccess"));
            Log.outputLog(response);
            Log.endLog(SERVICE_NAME, CommonConstant.METHOD_DELETE);
            return response;
        } catch (Exception e) {
            Log.errorLog(e);
            Log.endLog(SERVICE_NAME, CommonConstant.METHOD_DELETE);
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
        setData(trip, request);
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
                || !Utils.isEmpty(request.getAddress()) || !Utils.objNull(request.getStartDate())
                || !Utils.objNull(request.getEndDate());
        }
        return !Utils.isEmpty(request.getId()) || !Utils.isEmpty(request.getTitle())
            || !Utils.isEmpty(request.getAddress()) || !Utils.objNull(request.getStartDate())
            || !Utils.objNull(request.getEndDate());
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
        Date currentDate = new Date();

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

        if (startDate.before(currentDate)) {
            Log.outputLog(request);
            Log.endLog(SERVICE_NAME, method);
            throw new BadRequestException(getMessage("DateCurrentFail", new Object[]{currentDate, startDate}));
        }
    }

    private void setData(Trip trip, TripDTO request) {
        trip.setDescription(request.getDescription());
        trip.setTitle(request.getTitle());
        trip.setStartDate(request.getStartDate());
        trip.setEndDate(request.getEndDate());
        trip.setAddress(request.getAddress());
        trip.setMembers(request.getMembers());
        trip.setOwner(userService.getUserByUserName().getUserId());
    }

    private void checkPermissionsTravel(TripDTO request) {
        String userSystem = userService.getUserByUserName().getUserId();
        boolean inTravel = memberServices.checkUserInTravel(userSystem, request.getId());
        boolean isOwner = tripRepository.getOwnerTrip(request.getId()).equals(userSystem);
        if (!inTravel && !isOwner) {
            throw new SystemErrorException(getMessage("Permissions"));
        }
    }

    /**
     * Call service save image.
     *
     * @param request TripDTO
     * @param trip Trip
     * */
    private void saveImage(TripDTO request, Trip trip) {
        try {
            for (String linkImage: request.getLinkImages()) {
                Image image = new Image();
                image.setTripId(trip.getId());
                image.setLink(linkImage);
                imageService.create(image);
            }
        } catch (Exception e) {
            Log.errorLog(e);
            Log.endLog(SERVICE_NAME, "saveImage");
            throw new SystemErrorException(getMessage("SaveImage"));
        }
    }
}
