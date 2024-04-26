package com.vocation.travel.dto;

import com.vocation.travel.entity.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FriendDTO {
    private String id;
    private User user;
    private String myFriend;
    private String status;
}
