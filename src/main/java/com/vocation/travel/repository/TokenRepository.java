package com.vocation.travel.repository;

import com.vocation.travel.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, String> {
  @Query(value = "SELECT  * FROM TOKEN t WHERE t.refresh = :rf", nativeQuery = true)
  Token getRfToken(String rf);

}
