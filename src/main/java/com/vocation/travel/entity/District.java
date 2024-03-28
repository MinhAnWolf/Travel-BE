package com.vocation.travel.entity;

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
}
