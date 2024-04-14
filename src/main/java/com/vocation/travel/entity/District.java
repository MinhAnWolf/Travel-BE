package com.vocation.travel.entity;

import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Table District.
 *
 * @author Minh An
 * */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "district")
public class District {
    @Id
    private String district_id;
    private String district_name;
    private String district_type;
    private String province_id;
    private String province_name;
}
