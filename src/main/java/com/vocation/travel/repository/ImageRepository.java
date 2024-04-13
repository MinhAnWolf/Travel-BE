package com.vocation.travel.repository;

import com.vocation.travel.entity.District;
import com.vocation.travel.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
}
