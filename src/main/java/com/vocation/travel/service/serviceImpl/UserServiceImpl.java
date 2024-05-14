package com.vocation.travel.service.serviceImpl;

import com.vocation.travel.config.ExceptionHandler;
import com.vocation.travel.config.ExceptionHandler.BadRequestException;
import com.vocation.travel.config.Message;
import com.vocation.travel.entity.User;
import com.vocation.travel.repository.UserRepository;
import com.vocation.travel.service.UserService;
import java.util.Optional;

import com.vocation.travel.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User service.
 *
 * @author Minh An
 * @version 0.0.1
 */
@Service
public class UserServiceImpl extends Message implements UserService {

  @Autowired
  private UserRepository userRepository;

  /**
   * Get user by id.
   *
   * @param id String
   * @return User
   * */
  @Override
  public User getUserById(String id) {
    Optional<User> user = userRepository.findById(id);
    return user.orElse(null);
  }

  /**
   * Get user by username is system.
   *
   * @return User
   * */
  @Override
  public User getUserByUserName() {
    Optional<User> user = userRepository.findByUsername(Utils.userSystem());
    if (user.isEmpty()) {
      throw new BadRequestException(getMessage("UserNotExist"));
    }
    return user.get();
  }

  /**
   * Get user id by username is system.
   *
   * @return User
   * */
  @Override
  public String getIdUserByUserName() throws BadRequestException {
    String idUser = userRepository.findUserIdByUsername(Utils.userSystem());
    if (Utils.isEmpty(idUser)) {
      throw new BadRequestException(getMessage("UserNotExist"));
    }
    return idUser;
  }
}
