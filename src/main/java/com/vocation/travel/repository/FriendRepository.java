package com.vocation.travel.repository;

import com.vocation.travel.dto.FriendDTO;
import com.vocation.travel.entity.District;
import com.vocation.travel.entity.Friend;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend, String> {
  @Query(value = "SELECT u.USER_NAME, u.USER_ID, f.ID FROM FRIEND f "
                + "INNER JOIN users u ON f.ID_USER = u.USER_ID "
                + "WHERE f.MY_FRIEND = :myFriend and f.STATUS = 'active'", nativeQuery = true)
  List<Object[]> getListFriendActive(String myFriend);

  @Query(value = "SELECT COUNT(*) FROM FRIEND WHERE ID_USER = :idUser", nativeQuery = true)
  Integer checkUserAdded(String idUser);
}
