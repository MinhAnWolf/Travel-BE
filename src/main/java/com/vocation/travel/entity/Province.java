package com.vocation.travel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Table Province.
 *
 * @author Minh An
 * */
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "province")
public class Province {
    @Id
    private String province_id;
    private String province_name;
    private String province_type;
    private String url_image;

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

    public String getProvince_type() {
        return province_type;
    }

    public void setProvince_type(String province_type) {
        this.province_type = province_type;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }
}
