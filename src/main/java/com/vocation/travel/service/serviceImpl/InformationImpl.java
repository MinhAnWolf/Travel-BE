package com.vocation.travel.service.serviceImpl;

import com.vocation.travel.common.Log;
import com.vocation.travel.config.Message;
import com.vocation.travel.config.ExceptionHandler.*;
import com.vocation.travel.dto.InformationDTO;
import com.vocation.travel.entity.Information;
import com.vocation.travel.model.BaseResponse;
import com.vocation.travel.repository.InformationRepository;
import com.vocation.travel.service.CRUD;
import com.vocation.travel.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.vocation.travel.common.constant.CodeConstant.RESPONSE_SUCCESS;

@Service
public class InformationImpl extends Message implements CRUD<InformationDTO, Information> {

    @Autowired
    private InformationRepository informationRepository;

    private final static String SERVICE_NAME = "InformationService";


    @Override
    public BaseResponse create(InformationDTO request) {
        final String METHOD_NAME = "create";
        try {
            Log.startLog(SERVICE_NAME, METHOD_NAME);
            Log.inputLog(request);
            Information info = checkEntity(request, METHOD_NAME);
            BaseResponse baseResponse;
//            info.setUserId(Utils.userSystem());
            informationRepository.save(info);
            baseResponse = new BaseResponse(RESPONSE_SUCCESS, Boolean.TRUE, getMessage("crateSuccess"));
            Log.outputLog(baseResponse);
            Log.endLog(SERVICE_NAME, METHOD_NAME);
            return baseResponse;
        } catch (Exception ex) {
            Log.outputLog(request);
            Log.endLog(SERVICE_NAME, METHOD_NAME);
            throw new BadRequestException(getMessage("CrateFail"));
        }
    }

    @Override
    public BaseResponse read(InformationDTO request) {
        final String METHOD_NAME = "read";
        try {
            Log.startLog(SERVICE_NAME, METHOD_NAME);
            Log.inputLog(request);
            BaseResponse baseResponse;
            baseResponse = new BaseResponse(RESPONSE_SUCCESS, informationRepository.findAll(), getMessage("readSuccess"));
            Log.outputLog(baseResponse);
            Log.endLog(SERVICE_NAME, METHOD_NAME);
            return baseResponse;
        } catch (Exception ex) {
            Log.outputLog(request);
            Log.endLog(SERVICE_NAME, METHOD_NAME);
            throw new BadRequestException(getMessage("readFail"));
        }
    }

    @Override
    public BaseResponse update(InformationDTO request) {
        final String METHOD_NAME = "update";
        try {
            Log.startLog(SERVICE_NAME, METHOD_NAME);
            Log.inputLog(request);
            Information info = checkEntity(request, METHOD_NAME);
            BaseResponse baseResponse;
            info.setUserId(Utils.userSystem());
            informationRepository.save(info);
            baseResponse = new BaseResponse(RESPONSE_SUCCESS, Boolean.TRUE, getMessage("updateSuccess"));
            Log.outputLog(baseResponse);
            Log.endLog(SERVICE_NAME, METHOD_NAME);
            return baseResponse;
        } catch (Exception ex) {
            Log.outputLog(request);
            Log.endLog(SERVICE_NAME, METHOD_NAME);
            throw new BadRequestException(getMessage("updateFail"));
        }
    }

    @Override
    public BaseResponse delete(InformationDTO request) {
        final String METHOD_NAME = "delete";
        try {
            Log.startLog(SERVICE_NAME, METHOD_NAME);
            Log.inputLog(request);
            Information info = checkEntity(request, METHOD_NAME);
            BaseResponse baseResponse;
            informationRepository.delete(info);
            baseResponse = new BaseResponse(RESPONSE_SUCCESS, Boolean.TRUE, getMessage("deleteSuccess"));
            Log.outputLog(baseResponse);
            Log.endLog(SERVICE_NAME, METHOD_NAME);
            return baseResponse;
        } catch (Exception ex) {
            Log.outputLog(request);
            Log.endLog(SERVICE_NAME, METHOD_NAME);
            throw new BadRequestException(getMessage("deleteFail"));
        }
    }

    private Information checkEntity(InformationDTO request, String method) {
        Information information = new Information();
        if (method.equals("create")) {
            information.setInfoId(null);
        } else {
            if (!checkInput(request)) {
                throw new BadRequestException(getMessage("ParamsNull"));
            }
        }
        information.setInfoName(request.getInfoName());
        information.setInfoBirthday(request.getInfoBirthday());
        information.setInfoGender(request.getInfoGender());
        information.setInfoEmail(request.getInfoEmail());
        information.setInfoPhone(request.getInfoPhone());
        return information;
    }

    private boolean checkInput(InformationDTO informationDTO){
        return !Utils.isEmpty(informationDTO.getInfoId()) && !Utils.isEmpty(informationDTO.getInfoName())
                && !Utils.isNull(informationDTO.getInfoBirthday()) && !Utils.isNull(informationDTO.getInfoGender())
                && !Utils.isEmpty(informationDTO.getInfoEmail()) && !Utils.isEmpty(informationDTO.getInfoPhone())
                && !Utils.isEmpty(informationDTO.getUserId());
    }
}