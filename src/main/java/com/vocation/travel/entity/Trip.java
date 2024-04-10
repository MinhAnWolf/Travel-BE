package com.vocation.travel.entity;

import jakarta.persistence.*;
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
@Entity(name = "TRIP")
public class Trip extends HelperBy {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESC")
    private String desc;

    @Column(name = "ID_PROVINCE")
    private String idProvince;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "ID_WARD")
    private Date idWard;

    @Column(name = "ID_DISTRICT")
    private Date idDistrict;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trip_detail_id", referencedColumnName = "id")
    private TripDetail tripDetail;
}
