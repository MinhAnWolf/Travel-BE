package com.vocation.travel.service;

import com.vocation.travel.entity.User;
import org.springframework.stereotype.Service;


public interface UserService {
  User getUserById(String id);
}
