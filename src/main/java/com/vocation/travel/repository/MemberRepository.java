package com.vocation.travel.repository;

import com.vocation.travel.dto.MemberDTO;
import com.vocation.travel.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, String>  {
  @Query(value = "SELECT * FROM MEMBER WHERE id_trip = :idTrip", nativeQuery = true)
  List<MemberDTO> getMemberByTravelDetailId(String idTrip);

  @Query(value = "SELECT COUNT(*) FROM MEMBER WHERE id = :idMember and id_trip = :idTrip", nativeQuery = true)
  Integer getMemberInTrip(String idMember, String idTrip);
}
