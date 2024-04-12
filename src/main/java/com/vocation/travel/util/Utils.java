package com.vocation.travel.util;

import java.util.Objects;

import com.vocation.travel.model.AuthUser;
import jakarta.servlet.http.Cookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *Utils support.
 *
 * @author Minh An
 * */
public class Utils {

  /**
   * Check is empty.
   *
   * @param value String
   * @return boolean
   * */
  public static boolean isEmpty(String value) {
    return value == null || value.isEmpty();
  }

  public static boolean objNull(Object obj) {
    return Objects.isNull(obj);
  }

  public static String getCookieOnly(String key, Cookie[] listCookie) {
    String result = null;
    if (!objNull(listCookie)) {
      for (Cookie cookie : listCookie) {
        if (key.equals(cookie.getName()) && cookie.isHttpOnly()) {
          result = cookie.getValue();
        }
      }
    }
    return result;
  }

  public static String userSystem() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getName();
  }
}
