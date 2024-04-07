package com.vocation.travel.entity;

import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Table District.
 *
 * @author Minh An
 * */
@Entity(name = "district")
public class District {
    @Id
    private String district_id;
    private String district_name;
    private String district_type;
    private String province_id;
    private String province_name;

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getDistrict_type() {
        return district_type;
    }

    public void setDistrict_type(String district_type) {
        this.district_type = district_type;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }
}
