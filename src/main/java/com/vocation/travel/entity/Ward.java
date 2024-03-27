package com.vocation.travel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Table Ward.
 *
 * @author Minh An
 * */
@Entity(name = "ward")
public class Ward {
    @Id
    private String ward_id;
    private String ward_name;
    private String ward_type;
    private String district_id;
    private String district_name;
    private String province_id;
    private String province_name;
}
