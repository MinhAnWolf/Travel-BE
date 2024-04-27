package com.vocation.travel.model;

import java.util.List;

import lombok.*;

/**
 * Model request by socket client.
 *
 * @author Minh An
 * @version 0.0.1
 * */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RequestMessage {
  private String idClient;
  private List<String> clientReceives;
  private String message;
}
