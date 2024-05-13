package com.vocation.travel.controller;

import com.vocation.travel.common.constant.ApiConstant;
import com.vocation.travel.dto.ImageDTO;
import com.vocation.travel.entity.Image;
import com.vocation.travel.model.UploadImage;
import com.vocation.travel.service.serviceImpl.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(ApiConstant.API_IMAGE)
public class ImageController {
  @Autowired
  private ImageServiceImpl imageService;

  @Value("${url.upload.image}")
  private String urlUpload;

  @PostMapping(ApiConstant.API_UPLOAD_IMAGE)
  public ResponseEntity<?> test(@RequestBody UploadImage uploadImage) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<?> entity = new HttpEntity<>(uploadImage, headers);
    return restTemplate.exchange(urlUpload, HttpMethod.POST, entity, String.class);
  }
}
