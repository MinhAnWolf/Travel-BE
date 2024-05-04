package com.vocation.travel.socket;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.socket.WebSocketSession;

/**
 * Save information socket for user on ram.
 *
 * @author Minh An
 * @version 0.0.1
 * */
public class MemorySocket {
  public static Map<String, WebSocketSession> infoSocketUser = new HashMap<>();
}
