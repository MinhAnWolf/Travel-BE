package com.vocation.travel.service.serviceImpl.internal;

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
    Token token = tokenRepository.getRfToken(request.getAccess());
    if (Utils.objNull(token) || Utils.objNull(token.getRefresh())) {
        throw new SystemErrorException("X");
    }
    return token;
  }

  @Override
  public Token update(Token request) {
    return null;
  }

  @Override
  public Token delete(Token request) {
    return null;
  }
}
