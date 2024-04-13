package com.vocation.travel.service.serviceImpl;

import com.vocation.travel.common.Log;
import com.vocation.travel.common.constant.CommonConstant;
import com.vocation.travel.config.Message;
import com.vocation.travel.config.ExceptionHandler.*;
import com.vocation.travel.dto.InformationDTO;
import com.vocation.travel.entity.Information;
import com.vocation.travel.model.BaseResponse;
import com.vocation.travel.repository.InformationRepository;
import com.vocation.travel.service.CRUD;
import com.vocation.travel.service.UserService;
import com.vocation.travel.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.vocation.travel.common.constant.CommonConstant.RESPONSE_SUCCESS;

@Service
public class InformationImpl extends Message implements CRUD<InformationDTO, BaseResponse> {

    @Autowired
    private InformationRepository informationRepository;

    @Autowired
    private UserService userService;

    private final static String SERVICE_NAME = "InformationService";

    @Override
    public BaseResponse create(InformationDTO request) {
        try {
            Log.startLog(SERVICE_NAME, CommonConstant.METHOD_CREATE);
            Log.inputLog(request);
            Information info = convertEntity(request, CommonConstant.METHOD_CREATE);
            info.setUserId(userService.getUserByUserName().getUserId());
            informationRepository.save(info);
            BaseResponse baseResponse = new BaseResponse(RESPONSE_SUCCESS, Boolean.TRUE, getMessage("crateSuccess"));
            Log.outputLog(baseResponse);
            Log.endLog(SERVICE_NAME, CommonConstant.METHOD_CREATE);
            return baseResponse;
        } catch (Exception ex) {
            Log.errorLog(ex.getMessage());
            Log.endLog(SERVICE_NAME, CommonConstant.METHOD_CREATE);
            throw new BadRequestException(getMessage("CrateFail"));
        }
    }

    @Override
    public BaseResponse read(InformationDTO request) {
        try {
            Log.startLog(SERVICE_NAME, CommonConstant.METHOD_READ);
            Log.inputLog(request);
            BaseResponse baseResponse = new BaseResponse(RESPONSE_SUCCESS, informationRepository.findAll(), getMessage("readSuccess"));
            Log.outputLog(baseResponse);
            Log.endLog(SERVICE_NAME, CommonConstant.METHOD_READ);
            return baseResponse;
        } catch (Exception ex) {
            Log.errorLog(ex.getMessage());
            Log.endLog(SERVICE_NAME, CommonConstant.METHOD_READ);
            throw new BadRequestException(getMessage("readFail"));
        }
    }

    @Override
    public BaseResponse update(InformationDTO request) {
        try {
            Log.startLog(SERVICE_NAME, CommonConstant.METHOD_UPDATE);
            Log.inputLog(request);
            Information info = convertEntity(request, CommonConstant.METHOD_UPDATE);
            BaseResponse baseResponse;
            info.setUserId(userService.getUserByUserName().getUserId());
            informationRepository.save(info);
            baseResponse = new BaseResponse(RESPONSE_SUCCESS, Boolean.TRUE, getMessage("updateSuccess"));
            Log.outputLog(baseResponse);
            Log.endLog(SERVICE_NAME, CommonConstant.METHOD_UPDATE);
            return baseResponse;
        } catch (Exception ex) {
            Log.errorLog(ex.getMessage());
            Log.endLog(SERVICE_NAME, CommonConstant.METHOD_UPDATE);
            throw new BadRequestException(getMessage("updateFail"));
        }
    }

    @Override
    public BaseResponse delete(InformationDTO request) {
        return null;
    }

    private Information convertEntity(InformationDTO request, String method) {
        Information information = new Information();
        if (method.equals(CommonConstant.METHOD_CREATE)) {
            information.setInfoId(null);
        } else {
            if (!checkInput(request)) {
                throw new BadRequestException(getMessage("ParamsNull"));
            }
        }
        setData(information, request);
        return information;
    }

    private boolean checkInput(InformationDTO informationDTO){
        return !Utils.isEmpty(informationDTO.getInfoId()) && !Utils.isEmpty(informationDTO.getInfoName())
                && !Utils.objNull(informationDTO.getInfoBirthday()) && !Utils.objNull(informationDTO.getInfoGender())
                && !Utils.isEmpty(informationDTO.getInfoEmail()) && !Utils.isEmpty(informationDTO.getInfoPhone())
                && !Utils.isEmpty(informationDTO.getUserId());
    }

    private void setData(Information information, InformationDTO request) {
        information.setInfoName(request.getInfoName());
        information.setInfoBirthday(request.getInfoBirthday());
        information.setInfoGender(request.getInfoGender());
        information.setInfoEmail(request.getInfoEmail());
        information.setInfoPhone(request.getInfoPhone());
    }
}