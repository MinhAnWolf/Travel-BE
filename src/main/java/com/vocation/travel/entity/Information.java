package com.vocation.travel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "information")
public class Information {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "INFO_ID")
    private String infoId;

    @Column(name = "INFO_NAME")
    private String infoName;

    @Column(name = "INFO_BIRTHDAY")
    private Date infoBirthday;

    @Column(name = "INFO_GENDER")
    private Integer infoGender;

    @Column(name = "INFO_EMAIL")
    private String infoEmail;

    @Column(name = "INFO_PHONE")
    private String infoPhone;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "PROVINCE_ID")
    private String provinceId;

}
