package com.vocation.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Manager socket session.
 *
 * @author Minh An
 * @version 0.0.1
 * */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ManagerSocketDTO {
  private String id;
  private String userId;
  private String sessionId;
}
