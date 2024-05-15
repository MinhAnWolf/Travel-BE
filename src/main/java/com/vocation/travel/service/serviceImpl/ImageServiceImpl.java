package com.vocation.travel.service.serviceImpl;

import com.vocation.travel.common.Log;
import com.vocation.travel.common.constant.CommonConstant;
import com.vocation.travel.config.ExceptionHandler.BadRequestException;
import com.vocation.travel.config.ExceptionHandler.SystemErrorException;
import com.vocation.travel.config.Message;
import com.vocation.travel.entity.Image;
import com.vocation.travel.model.BaseResponse;
import com.vocation.travel.repository.ImageRepository;
import com.vocation.travel.service.CRUD;
import com.vocation.travel.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Image service.
 *
 * @author Minh An
 * @version v0.0.1
 * */
@Service
public class ImageServiceImpl extends Message implements CRUD<Image, BaseResponse> {
    @Autowired
    private ImageRepository imageRepository;

    private final static String SERVICE_NAME = "ImageService";

    /**
     * Save image.
     *
     * @param request ImageDTO
     * @return BaseResponse
     * */
    @Override
    public BaseResponse create(Image request) {
        Log.startLog(SERVICE_NAME, CommonConstant.METHOD_CREATE);
        Log.inputLog(request);
        try {
            //Check input params
            checkInputParams(request);
            // Save information image
            imageRepository.save(request);
            BaseResponse response = new BaseResponse(CommonConstant.RESPONSE_SUCCESS, Boolean.TRUE, getMessage("CreateSuccess"));
            Log.outputLog(response);
            Log.endLog(SERVICE_NAME, CommonConstant.METHOD_CREATE);
            return response;
        } catch (Exception e) {
            Log.errorLog(e);
            Log.endLog(SERVICE_NAME, CommonConstant.METHOD_CREATE);
            throw new SystemErrorException("CreateFail");
        }
    }

    @Override
    public BaseResponse read(Image request) {
        return null;
    }

    @Override
    public BaseResponse update(Image request) {
        return null;
    }

    @Override
    public BaseResponse delete(Image request) {
        return null;
    }

    /**
     * Check input params.
     *
     * @param image Image
     * */
    private void checkInputParams(Image image) {
        if (Utils.objNull(image) || Utils.isEmpty(image.getLink())
            || Utils.isEmpty(image.getTripId())) {
            Log.endLog(SERVICE_NAME, "saveImage");
            throw new BadRequestException(getMessage("SaveImage"));
        }
    }
}
