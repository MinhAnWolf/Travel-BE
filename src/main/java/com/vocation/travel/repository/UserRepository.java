package com.vocation.travel.repository;

import com.vocation.travel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT USER_ID FROM USERS WHERE USER_NAME = :username", nativeQuery = true)
    String findUserIdByUsername(String username);

    @Query(value = "SELECT USER_NAME FROM USERS WHERE USER_ID = :userId", nativeQuery = true)
    String getUsernameById(String userId);
}
