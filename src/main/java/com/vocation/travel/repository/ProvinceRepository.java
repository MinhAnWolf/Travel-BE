package com.vocation.travel.repository;

import com.vocation.travel.entity.District;
import com.vocation.travel.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinceRepository extends JpaRepository<Province, String> {
}
