package com.vocation.travel.socket;

import com.vocation.travel.model.BaseResponse;
import com.vocation.travel.service.CRUD;
import com.vocation.travel.service.UserService;
import com.vocation.travel.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Service
public class SocketHandle extends TextWebSocketHandler {
  @Autowired
  private UserService userService;

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    MemorySocket.infoSocketUser.put(getUID(session), session);
  }

  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    session.sendMessage(message);
  }

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    session.sendMessage(message);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    MemorySocket.infoSocketUser.put(getUID(session), null);
  }

  private String getUID(WebSocketSession session) throws IOException {
    String uid = session.getHandshakeHeaders().getFirst("c_id");
    if (Utils.isEmpty(uid)) {
      session.close(CloseStatus.BAD_DATA);
    }
    return uid;
  }
}
