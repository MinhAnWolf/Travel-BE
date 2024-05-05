package com.vocation.travel.service.serviceImpl;

import com.vocation.travel.common.constant.CommonConstant;
import com.vocation.travel.config.Message;
import com.vocation.travel.dto.ImageDTO;
import com.vocation.travel.entity.Image;
import com.vocation.travel.model.BaseResponse;
import com.vocation.travel.repository.ImageRepository;
import com.vocation.travel.service.CRUD;
import com.vocation.travel.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl extends Message implements CRUD<ImageDTO, BaseResponse>, ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public BaseResponse create(ImageDTO request) {
        imageRepository.save(convertEntity(request));
        return new BaseResponse(CommonConstant.RESPONSE_SUCCESS, CommonConstant.RESPONSE_SUCCESS, getMessage("CreateSuccess"));
    }

    @Override
    public BaseResponse read(ImageDTO request) {
        return null;
    }

    @Override
    public BaseResponse update(ImageDTO request) {
        return null;
    }

    @Override
    public BaseResponse delete(ImageDTO request) {
        return null;
    }

    @Override
    public BaseResponse uploadImage() {
        return null;
    }

    private Image convertEntity(ImageDTO request) {
        Image image = new Image();
        image.setLinkImage(request.getLinkImage());
        image.setTrip(request.getTrip());
        return image;
    }


}
