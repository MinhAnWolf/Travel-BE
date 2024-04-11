package com.vocation.travel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TRIP")
public class Trip extends HelperBy {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "ID_WARD")
    private Date idWard;

    @Column(name = "ID_DISTRICT")
    private Date idDistrict;

    @Column(name = "IMAGE")
    private String image;

    @OneToMany(mappedBy = "trip")
    private List<Member> members;
}
