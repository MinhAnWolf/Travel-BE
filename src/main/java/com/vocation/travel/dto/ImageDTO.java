package com.vocation.travel.dto;

import com.vocation.travel.entity.Trip;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class    ImageDTO {
    private String id;
    private String linkImage;
    private Trip trip;
}
