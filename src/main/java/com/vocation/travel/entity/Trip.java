package com.vocation.travel.entity;

import com.vocation.travel.common.constant.CommonConstant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;

import static com.vocation.travel.util.DateTimeUtils.checkStartTimeAfterFinishTime;

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

    @Column(name = "STATUS")
    private String status;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "ID_WARD")
    private Date idWard;

    @Column(name = "ID_DISTRICT")
    private Date idDistrict;

    @Column(name = "OWNER")
    private String owner;

    @OneToMany(mappedBy = "trip")
    private List<Member> members;

    @OneToMany(mappedBy = "trip")
    private List<Image> images;

    @PrePersist
    @PreUpdate
    private void updateStatus() {
        Date currentDate = new Date();
        if (currentDate.before(startDate)) {
            status = CommonConstant.StatusTrip.TODO;
        } else if (currentDate.after(startDate) && currentDate.before(endDate)) {
            status = CommonConstant.StatusTrip.PROCESS;
        } else if (currentDate.after(endDate)) {
            status = CommonConstant.StatusTrip.FINISH;
        } else {
            status = CommonConstant.StatusTrip.DELAY;
        }
    }
}
