package com.vocation.travel.service.serviceImpl.internal;

import com.vocation.travel.common.Log;
import com.vocation.travel.common.constant.CommonConstant;
import com.vocation.travel.config.ExceptionHandler;
import com.vocation.travel.config.ExceptionHandler.BadRequestException;
import com.vocation.travel.config.ExceptionHandler.SystemErrorException;
import com.vocation.travel.entity.Token;
import com.vocation.travel.repository.TokenRepository;
import com.vocation.travel.service.CRUD;
import com.vocation.travel.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Storage token save database
 *
 * @author Minh An
 * */
@Service
public class StorageTokenServiceImpl implements CRUD<Token, Token> {
  @Autowired
  private TokenRepository tokenRepository;

  private final static String SERVICE_NAME = "StorageTokenService";

  @Override
  public Token create(Token request) {
    tokenRepository.save(request);
    return null;
  }

  /**
   * Get token by refresh token
   * @param request Token
   * @return Token
   * */
  @Override
  public Token read(Token request) {
    Log.startLog(SERVICE_NAME, CommonConstant.METHOD_READ);
    Log.inputLog(request);
    checkInputParams(request);
    try {
      Token token = tokenRepository.getRfToken(request.getAccess());
      Log.outputLog(token);
      Log.endLog(SERVICE_NAME, CommonConstant.METHOD_READ);
      return token;
    } catch (Exception e) {
      Log.errorLog(e);
      Log.endLog(SERVICE_NAME, CommonConstant.METHOD_READ);
      throw new SystemErrorException("SystemErr");
    }
  }

  @Override
  public Token update(Token request) {
    return null;
  }

  @Override
  public Token delete(Token request) {
    return null;
  }

  private void checkInputParams(Token token) {
    if (Utils.objNull(token) || Utils.objNull(token.getRefresh())) {
      throw new BadRequestException("BadRequest");
    }
  }
}
