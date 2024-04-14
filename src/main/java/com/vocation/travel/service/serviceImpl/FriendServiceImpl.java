package com.vocation.travel.service.serviceImpl;

import com.vocation.travel.config.Message;
import com.vocation.travel.dto.FriendDTO;
import com.vocation.travel.entity.Friend;
import com.vocation.travel.model.BaseResponse;
import com.vocation.travel.repository.FriendRepository;
import com.vocation.travel.service.CRUD;
import com.vocation.travel.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendServiceImpl extends Message implements CRUD<FriendDTO, BaseResponse> {
    @Autowired
    private FriendRepository friendRepository;

    @Override
    public BaseResponse create(FriendDTO request) {
        friendRepository.save(convertEntity(request));
        return new BaseResponse();
    }

    @Override
    public BaseResponse read(FriendDTO request) {
        return null;
    }

    @Override
    public BaseResponse update(FriendDTO request) {
        return null;
    }

    @Override
    public BaseResponse delete(FriendDTO request) {
        return null;
    }

    private Friend convertEntity(FriendDTO request) {
        Friend friend = new Friend();
        request.setMyFriend(request.getMyFriend());
        request.setId(Utils.userSystem());
        return friend;
    }
}
