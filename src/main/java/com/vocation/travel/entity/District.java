package com.vocation.travel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Table District.
 *
 * @author Minh An
 * */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "district")
public class District {
    @Id
    private String district_id;
    private String district_name;
    private String district_type;
    private String province_id;
    private String province_name;
}
