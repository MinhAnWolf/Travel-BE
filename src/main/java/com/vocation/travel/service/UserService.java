package com.vocation.travel.service;

import com.vocation.travel.entity.User;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

/**
 * User service.
 *
 * @author Minh An
 * @version 0.0.1
 */
public interface UserService {
  User getUserById(String id);

  User getUserByUserName();

//  User update(User user);

  String getIdUserByUserName() throws BadRequestException;
}
