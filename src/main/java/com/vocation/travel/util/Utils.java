package com.vocation.travel.util;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vocation.travel.model.AuthUser;
import jakarta.servlet.http.Cookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

/**
 *Utils support.
 *
 * @author Minh An
 * */
public class Utils {

  /**
   * Check is empty type string.
   *
   * @param value String
   * @return boolean
   * */
  public static boolean isEmpty(String value) {
    return value == null || value.isEmpty();
  }

  /**
   * Check null type object.
   *
   * @param obj Object
   * @return boolean
   * */
  public static boolean objNull(Object obj) {
    return Objects.isNull(obj);
  }

  /**
   * Check null type date.
   *
   * @param date Date
   * @return boolean
   */
  public static boolean dateNull(Date date) {
    return objNull(date);
  }

  /**
   * Check null type Boolean
   *
   * @param gender Boolean
   * @return boolean
   */
  public static boolean booleanNull(Boolean gender) {
    return objNull(gender);
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

  /**
   * Deserialize JSON to object.
   *
   * @param json String
   * @param type Class<E>
   * @return <E> E
   * */
  public static <E> E deserializedJson(String json, Class<E> type) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readValue(json, type);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Deserialize object to json.
   *
   * @param object String
   * @return String
   * */
  public static String deserializedObj(Object object) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
