package com.vocation.travel.repository;

import com.vocation.travel.dto.InformationDTO;
import com.vocation.travel.entity.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InformationRepository extends JpaRepository<Information, String> {
  @Query(value = "SELECT * FROM INFORMATION WHERE user_id = :userId", nativeQuery = true)
  public InformationDTO getInformationByUserId(String userId);
}
