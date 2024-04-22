package com.vocation.travel.service.serviceImpl;

import com.vocation.travel.common.Log;
import com.vocation.travel.common.constant.CommonConstant;
import com.vocation.travel.common.constant.CommonConstant.ProcessStatus;
import com.vocation.travel.common.constant.CommonConstant.StatusFriend;
import com.vocation.travel.config.ExceptionHandler.SystemErrorException;
import com.vocation.travel.config.Message;
import com.vocation.travel.dto.FriendDTO;
import com.vocation.travel.entity.Friend;
import com.vocation.travel.model.BaseResponse;
import com.vocation.travel.repository.FriendRepository;
import com.vocation.travel.service.CRUD;
import com.vocation.travel.util.Utils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendServiceImpl extends Message implements CRUD<FriendDTO, BaseResponse> {
    @Autowired
    private FriendRepository friendRepository;

    private final String SERVICE_NAME = "MemberService";


    @Override
    public BaseResponse create(FriendDTO request) {
        Log.startLog(SERVICE_NAME, CommonConstant.METHOD_CREATE);
        Log.inputLog(request);
        checkInputParams(request);
        try {
            friendRepository.save(convertEntity(request, StatusFriend.PENDING));
            BaseResponse response = new BaseResponse(CommonConstant.RESPONSE_SUCCESS,
                ProcessStatus.Success, getMessage("CreateSuccess"));
            Log.outputLog(response);
            Log.endLog(SERVICE_NAME, CommonConstant.METHOD_UPDATE);
            return response;
        } catch (Exception e) {
            Log.errorLog(e.getMessage());
            Log.endLog(SERVICE_NAME, CommonConstant.METHOD_CREATE);
            throw new SystemErrorException(getMessage("SystemErr"));
        }
    }

    @Override
    public BaseResponse read(FriendDTO request) {
        Log.startLog(SERVICE_NAME, CommonConstant.METHOD_READ);
        Log.inputLog(request);
        try {
            BaseResponse response = new BaseResponse(CommonConstant.RESPONSE_SUCCESS,
                friendRepository.getListFriendActive(Utils.userSystem()), getMessage("ReadSuccess"));
            Log.outputLog(response);
            Log.endLog(SERVICE_NAME, CommonConstant.METHOD_UPDATE);
            return response;
        } catch (Exception e) {
            Log.errorLog(e.getMessage());
            Log.endLog(SERVICE_NAME, CommonConstant.METHOD_CREATE);
            throw new SystemErrorException(getMessage("SystemErr"));
        }
    }

    @Override
    public BaseResponse update(FriendDTO request) {
        return create(request);
    }

    @Override
    public BaseResponse delete(FriendDTO request) {
        Log.startLog(SERVICE_NAME, CommonConstant.METHOD_DELETE);
        Log.inputLog(request);
        try {
            friendRepository.delete(convertEntity(request, request.getMyFriend()));
            BaseResponse response = new BaseResponse(CommonConstant.RESPONSE_SUCCESS,
                ProcessStatus.Success, getMessage("DeleteSuccess"));
            Log.outputLog(response);
            Log.endLog(SERVICE_NAME, CommonConstant.METHOD_DELETE);
            return response;
        } catch (Exception e) {
            Log.errorLog(e.getMessage());
            Log.endLog(SERVICE_NAME, CommonConstant.METHOD_CREATE);
            throw new SystemErrorException(getMessage("SystemErr"));
        }
    }

    private Friend convertEntity(FriendDTO request, String statusFriend) {
        Friend friend = new Friend();
        friend.setMyFriend(request.getMyFriend());
        friend.setId(Utils.userSystem());
        friend.setStatus(statusFriend);
        return friend;
    }

    private void checkInputParams(FriendDTO request) {

    }
}
