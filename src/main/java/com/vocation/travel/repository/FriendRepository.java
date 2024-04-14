package com.vocation.travel.repository;

import com.vocation.travel.entity.District;
import com.vocation.travel.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, String> {
}
