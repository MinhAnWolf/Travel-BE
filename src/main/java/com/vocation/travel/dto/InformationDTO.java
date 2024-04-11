package com.vocation.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InformationDTO {
    private String infoId;
    private String infoName;
    private Date infoBirthday;
    private Integer infoGender;
    private String infoEmail;
    private String infoPhone;
    private String userId;
    private String provinceId;

}
