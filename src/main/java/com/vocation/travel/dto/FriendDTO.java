package com.vocation.travel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vocation.travel.entity.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FriendDTO {
    private String id;
    private String myFriend;
    private String status;
    private UsersDTO usersDto;
}
