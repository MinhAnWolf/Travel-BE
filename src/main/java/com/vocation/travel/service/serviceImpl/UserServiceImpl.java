package com.vocation.travel.service.serviceImpl;

import com.vocation.travel.common.Log;
import com.vocation.travel.common.constant.CommonConstant;
import com.vocation.travel.config.ExceptionHandler;
import com.vocation.travel.config.ExceptionHandler.BadRequestException;
import com.vocation.travel.config.Message;
import com.vocation.travel.dto.TripDTO;
import com.vocation.travel.dto.UsersDTO;
import com.vocation.travel.entity.Trip;
import com.vocation.travel.entity.User;
import com.vocation.travel.model.BaseResponse;
import com.vocation.travel.repository.UserRepository;
import com.vocation.travel.service.CRUD;
import com.vocation.travel.service.UserService;
import java.util.Optional;

import com.vocation.travel.util.RegexPattern;
import com.vocation.travel.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.vocation.travel.common.constant.CommonConstant.RESPONSE_SUCCESS;

/**
 * User service.
 *
 * @author Minh An
 * @version 0.0.1
 */
@Service
public class UserServiceImpl extends Message implements UserService, CRUD<UsersDTO, BaseResponse> {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private final static String SERVICE_NAME = "UserService";


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
   * Create user.
   *
   * @param request UserDTO
   * @return BaseResponse
   * */
  @Override
  public BaseResponse create(UsersDTO request) {
    return null;
  }

  /**
   * Read user.
   *
   * @param request UserDTO
   * @return BaseResponse
   * */
  @Override
  public BaseResponse read(UsersDTO request) {
    return null;
  }

  /**
   * Update user.
   *
   * @param request UserDTO
   * @return BaseResponse
   * */
  @Override
  public BaseResponse update(UsersDTO request) {
    try {
      Log.startLog(SERVICE_NAME, CommonConstant.METHOD_UPDATE);
      Log.inputLog(request);
      Optional<User> optionalUser = userRepository.findById(request.getId());

      if (optionalUser.isPresent()) {
        User user = optionalUser.get();
        BaseResponse response;
        if (!Utils.isEmpty(request.getUsername())) {
          user.setUsername(request.getUsername());
        }
        if (!Utils.isEmpty(request.getFullName())) {
          user.setInfoName(request.getFullName());
        }
        if (!Utils.isEmpty(request.getEmail()) && RegexPattern.regexEmail(request.getEmail())) {
          user.setEmail(request.getEmail());
        }
        if (!Utils.isEmpty(request.getPassword())) {
          user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (!Utils.isEmpty(request.getPhone()) && RegexPattern.regexPhone(request.getPhone())) {
          user.setInfoPhone(request.getPhone());
        }
        if (!Utils.objNull(request.getAvatar())) {
          user.setAvatar(request.getAvatar());
        }

        if (!Utils.dateNull(request.getBirthday())) {
          user.setInfoBirthday(request.getBirthday());
        }

        if (!Utils.booleanNull(request.getGender())) {
          user.setInfoGender(request.getGender());
        }

        userRepository.save(user);
        response = new BaseResponse(RESPONSE_SUCCESS, Boolean.TRUE, getMessage("UpdateSuccess"));
        Log.outputLog(response);
        Log.endLog(SERVICE_NAME, CommonConstant.METHOD_UPDATE);
        return response;
      } else {
        throw new ExceptionHandler.SystemErrorException(getMessage("UserNotFound"));
      }
    } catch (Exception e) {
      Log.errorLog(e);
      Log.endLog(SERVICE_NAME, CommonConstant.METHOD_UPDATE);
      throw new ExceptionHandler.SystemErrorException(getMessage("UpdateFail"));
    }
  }

  /**
   * Delete user.
   *
   * @param request UserDTO
   * @return BaseResponse
   * */
  @Override
  public BaseResponse delete(UsersDTO request) {
    return null;
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
