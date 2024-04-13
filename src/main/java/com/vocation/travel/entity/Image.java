package com.vocation.travel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "IMAGE")
public class Image {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "LINK_IMAGE")
    private String linkImage;

    @ManyToOne
    @JoinColumn(name = "ID_TRIP")
    private Trip trip;
}
