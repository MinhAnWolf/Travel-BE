package com.vocation.travel.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *Notification model.
 *
 * @author Minh An
 * @version 0.0.1
 * */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Notification {
  private String message;
  private List<String> receiveUserId;
}
