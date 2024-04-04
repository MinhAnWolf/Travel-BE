package com.vocation.travel.service.serviceImpl;

import com.vocation.travel.entity.User;
import com.vocation.travel.repository.UserRepository;
import com.vocation.travel.service.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public User getUserById(String id) {
    Optional<User> user = userRepository.findById(id);
    return user.orElse(null);
  }
}
