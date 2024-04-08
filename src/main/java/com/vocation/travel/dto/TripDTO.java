package com.vocation.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripDTO {
    private String id;
    private String title;
    private String idProvince;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String createBy;
}
