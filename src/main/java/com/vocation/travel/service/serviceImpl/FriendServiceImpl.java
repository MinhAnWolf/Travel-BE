package com.vocation.travel.service.serviceImpl;

import com.vocation.travel.common.Log;
import com.vocation.travel.common.constant.CommonConstant;
import com.vocation.travel.common.constant.CommonConstant.ProcessStatus;
import com.vocation.travel.common.constant.CommonConstant.StatusFriend;
import com.vocation.travel.config.ExceptionHandler.SystemErrorException;
import com.vocation.travel.config.Message;
import com.vocation.travel.dto.FriendDTO;
import com.vocation.travel.dto.UsersDTO;
import com.vocation.travel.entity.Friend;
import com.vocation.travel.entity.User;
import com.vocation.travel.model.BaseResponse;
import com.vocation.travel.model.Notification;
import com.vocation.travel.notification.service.NotificationService;
import com.vocation.travel.repository.FriendRepository;
import com.vocation.travel.repository.UserRepository;
import com.vocation.travel.service.CRUD;
import com.vocation.travel.util.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendServiceImpl extends Message implements CRUD<FriendDTO, BaseResponse> {
    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    private final String SERVICE_NAME = "FriendService";

    /**
     * Add friend.
     *
     * @param request FriendDTO
     * @return BaseResponse
     * */
    @Override
    public BaseResponse create(FriendDTO request) {
        Log.startLog(SERVICE_NAME, CommonConstant.METHOD_CREATE);
        Log.inputLog(request);
        checkInputParams(request);
        checkAddYourSelf(request);
        checkAddDuplicate(request);
        try {
            request.setMyFriend(Utils.userSystem());
            friendRepository.save(convertEntity(request, StatusFriend.PENDING));
            BaseResponse response = new BaseResponse(CommonConstant.RESPONSE_SUCCESS,
                ProcessStatus.Success, getMessage("CreateSuccess"));
            //set value notification
            Notification notification = new Notification();
            notification.setMessage(getMessage("NotificationFriend", new Object[]{Utils.userSystem()}));
            notification.setReceiveUserId(Collections.singletonList(request.getUsersDto().getId()));
            notificationService.sendNotification(notification);
            Log.outputLog(response);
            Log.endLog(SERVICE_NAME, CommonConstant.METHOD_CREATE);
            return response;
        } catch (Exception e) {
            Log.errorLog(e);
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
                repoConvertDto(friendRepository.getListFriendActive(Utils.userSystem())), getMessage("ReadSuccess"));
            Log.outputLog(response);
            Log.endLog(SERVICE_NAME, CommonConstant.METHOD_UPDATE);
            return response;
        } catch (Exception e) {
            Log.errorLog(e);
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
            Log.errorLog(e);
            Log.endLog(SERVICE_NAME, CommonConstant.METHOD_CREATE);
            throw new SystemErrorException(getMessage("SystemErr"));
        }
    }

    private Friend convertEntity(FriendDTO request, String statusFriend) {
        Friend friend = new Friend();
        friend.setMyFriend(request.getMyFriend());
        friend.setUser(UsersDTO.UserDtoConvertToUser(request.getUsersDto()));
        friend.setStatus(statusFriend);
        return friend;
    }

    private void checkInputParams(FriendDTO request) {

    }

    private void checkAddDuplicate(FriendDTO request) {
      int check = friendRepository.checkUserAdded(request.getUsersDto().getId());
      if (check > 0) {
        throw new SystemErrorException(getMessage("ExistFriend"));
      }
    }

    private void checkAddYourSelf(FriendDTO request) {
      Optional<User> user = userRepository.findByUsername(Utils.userSystem());
      if(user.isPresent()) {
        if (request.getUsersDto().getId().equals(user.get().getUserId())) {
          throw new SystemErrorException(getMessage("AddFriendSelf"));
        }
      }
    }

    private List<FriendDTO> repoConvertDto(List<Object[]> list) {
        List<FriendDTO> friendDtoList = new ArrayList<>();
        for (Object[] result: list) {
            FriendDTO friendDto = new FriendDTO();
            User user = new User();
            user.setUsername(String.valueOf(result[0]));
            user.setUserId(String.valueOf(result[1]));
            friendDto.setId(String.valueOf(result[2]));
            Optional<User> getUser = userRepository.findById(user.getUserId());
            if (getUser.isPresent()) {
                User valueUser = getUser.get();
                UsersDTO usersDto = new UsersDTO();
                usersDto.setId(valueUser.getUserId());
                usersDto.setAvatar(valueUser.getAvatar());
                usersDto.setFullName(valueUser.getInfoName());
                friendDto.setUsersDto(usersDto);
            }

            friendDtoList.add(friendDto);
        }
        return friendDtoList;
    }
}
