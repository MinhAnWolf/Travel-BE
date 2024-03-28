package com.vocation.travel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Table Province.
 *
 * @author Minh An
 * */
@Entity(name = "province")
public class Province {
    @Id
    private String province_id;
    private String province_name;
    private String province_type;
    private String url_image;
}
