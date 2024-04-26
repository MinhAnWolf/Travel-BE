package com.vocation.travel.service.serviceImpl;

import com.vocation.travel.common.Log;
import com.vocation.travel.common.constant.CommonConstant;
import com.vocation.travel.common.constant.CommonConstant.ProcessStatus;
import com.vocation.travel.config.ExceptionHandler.SystemErrorException;
import com.vocation.travel.config.Message;
import com.vocation.travel.dto.ManagerSocketDTO;
import com.vocation.travel.entity.ManagerSocket;
import com.vocation.travel.model.BaseResponse;
import com.vocation.travel.repository.ManagerSocketRepository;
import com.vocation.travel.service.CRUD;
import org.apache.catalina.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerSocketServiceImpl extends Message implements CRUD<ManagerSocketDTO, BaseResponse> {
  @Autowired
  private ManagerSocketRepository managerSocketRepository;

  private final String SERVICE_NAME = "FriendService";
  @Override
  public BaseResponse create(ManagerSocketDTO request) {
    Log.startLog(SERVICE_NAME, CommonConstant.METHOD_CREATE);
    Log.inputLog(request);
    try {
      managerSocketRepository.save(convertEntity(request));
      BaseResponse response = new BaseResponse(CommonConstant.RESPONSE_SUCCESS,
          ProcessStatus.Success, getMessage("CreateSuccess"));
      Log.outputLog(response);
      Log.endLog(SERVICE_NAME, CommonConstant.METHOD_CREATE);
      return response;
    } catch (Exception e) {
      Log.errorLog(e.getMessage());
      Log.endLog(SERVICE_NAME, CommonConstant.METHOD_CREATE);
      throw new SystemErrorException(getMessage("SystemErr"));
    }
  }

  @Override
  public BaseResponse read(ManagerSocketDTO request) {
    return null;
  }

  @Override
  public BaseResponse update(ManagerSocketDTO request) {
    return null;
  }

  @Override
  public BaseResponse delete(ManagerSocketDTO request) {
    return null;
  }

  private ManagerSocket convertEntity(ManagerSocketDTO request) {
    ManagerSocket managerSocket = new ManagerSocket();
    managerSocket.setSessionId(request.getSessionId());
    managerSocket.setUserId(request.getUserId());
    return managerSocket;
  }
}
