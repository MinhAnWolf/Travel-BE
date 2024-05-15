package com.vocation.travel.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate Support.
 *
 * @author Minh An
 * @version v0.0.1
 * */
@Service
public class RestUtil {
  /**
   * RestTemplate.
   *
   * @param url String
   * @param method HttpMethod
   * @param body Object
   *
   * @return ResponseEntity<String>
   * */
  public ResponseEntity<String> rest(String url, HttpMethod method, Object body) {
    RestTemplate restTemplate = new RestTemplate();
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    headers.set("Content-Type", "application/json");
    HttpEntity<?> httpEntity = new HttpEntity<>(body, headers);
    return restTemplate.exchange(url, method, httpEntity, String.class);
  }
}
