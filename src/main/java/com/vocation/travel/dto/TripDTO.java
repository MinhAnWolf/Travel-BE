package com.vocation.travel.dto;

import com.vocation.travel.entity.HelperBy;
import com.vocation.travel.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripDTO extends HelperBy {
    private String id;
    private String title;
    private String description;
    private String address;
    private String image;
    private Date startDate;
    private Date endDate;
    private String owner;
    private List<Member> members;
}
