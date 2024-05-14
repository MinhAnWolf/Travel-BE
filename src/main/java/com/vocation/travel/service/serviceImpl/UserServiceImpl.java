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
//        User user = new User();
        User user = optionalUser.get();
        BaseResponse response;
        if (request.getUsername() != null) {
          user.setUsername(request.getUsername());
        }
        if (request.getFullName() != null) {
          user.setInfoName(request.getFullName());
        }
        if (request.getEmail() != null) {
          user.setEmail(request.getEmail());
        }
        if (request.getPassword() != null) {
          user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getPhone() != null) {
          user.setInfoPhone(request.getPhone());
        }
        if (request.getAvatar() != null) {
          user.setAvatar(request.getAvatar());
        }
        if (request.getBirthday() != null) {
          user.setInfoBirthday(request.getBirthday());
        }
        if (request.getGender() != null) {
          user.setInfoGender(request.getGender());
        }
        userRepository.save(user);
        response = new BaseResponse(RESPONSE_SUCCESS, Boolean.TRUE, getMessage("UpdateSuccess"));
        Log.outputLog(response);
        Log.endLog(SERVICE_NAME, CommonConstant.METHOD_UPDATE);
        return response;
      } else {
        // Không tìm thấy người dùng với id tương ứng
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

  //////////////////////////////////
//  private User convertEntity(UsersDTO request, String method) {
//    User user = new User();
//    if (method.equals(method)) {
//      user.setUserId(null);
//    } else {
//      if (!checkInputParams(request, method)) {
//        throw new BadRequestException(getMessage("ParamsNull"));
//      }
//    }
//    setData(user, request);
//    return user;
//  }
}
