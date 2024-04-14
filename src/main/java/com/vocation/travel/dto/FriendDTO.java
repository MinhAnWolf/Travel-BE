package com.vocation.travel.dto;

import com.vocation.travel.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FriendDTO {
    private String id;
    private User user;
    private String myFriend;
}
