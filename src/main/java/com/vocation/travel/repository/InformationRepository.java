package com.vocation.travel.repository;

import com.vocation.travel.entity.Information;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformationRepository extends JpaRepository<Information, String> {
}
