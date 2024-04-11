package com.vocation.travel.repository;

import com.vocation.travel.dto.MemberDTO;
import com.vocation.travel.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, String>  {
//  @Query(value = "", nativeQuery = true)
//  public List<MemberDTO> getMemberByTravelDetailId();
}
