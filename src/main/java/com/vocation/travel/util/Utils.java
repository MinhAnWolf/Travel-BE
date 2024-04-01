package com.vocation.travel.util;

import java.util.Objects;
import jakarta.servlet.http.Cookie;

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
}
