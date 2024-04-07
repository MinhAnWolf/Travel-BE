package com.vocation.travel.util;

import java.util.Objects;

import com.vocation.travel.model.AuthUser;
import jakarta.servlet.http.Cookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 *
 * */
public class Utils {

  /**
   *
   * */
  public static boolean isEmpty(String value) {
    return value == null || value.isEmpty();
  }

  public static boolean isNull(Object obj) {
    return Objects.isNull(obj);
  }

  public static String getCookieOnly(String key, Cookie[] listCookie) {
    String result = null;
    if (!isNull(listCookie)) {
      for (Cookie cookie : listCookie) {
        if (key.equals(cookie.getName()) && cookie.isHttpOnly()) {
          result = cookie.getValue();
        }
      }
    }
    return result;
  }

  public static boolean checkIsUser(String uidRequest) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    AuthUser userDetails = (AuthUser) authentication.getPrincipal();
    String idUContext = userDetails.getUser().getUserId();
    if (uidRequest.equals(idUContext)) {
      return true;
    }
    return false;
  }
}
