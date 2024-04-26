package com.vocation.travel.repository;

import com.vocation.travel.entity.District;
import com.vocation.travel.entity.Friend;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FriendRepository extends JpaRepository<Friend, String> {
  @Query(value = "SELECT * FROM FRIEND WHERE ID_USER = :idUser and STATUS = 'active'", nativeQuery = true)
  List<Friend> getListFriendActive(String idUser);

  @Query(value = "SELECT COUNT(*) FROM FRIEND WHERE ID_USER = :idUser", nativeQuery = true)
  Integer checkUserAdded(String idUser);
}
