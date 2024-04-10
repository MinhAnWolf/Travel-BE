package com.vocation.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripDTO {
    private String id;
    private String title;
    private String desc;
    private String idProvince;
    private Date startDate;
    private Date endDate;
    private String createBy;
}
