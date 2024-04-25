package com.vocation.travel.socket;

import com.vocation.travel.config.ExceptionHandler.BadRequestException;
import com.vocation.travel.dto.ManagerSocketDTO;
import com.vocation.travel.model.BaseResponse;
import com.vocation.travel.model.RequestMessage;
import com.vocation.travel.service.CRUD;
import com.vocation.travel.service.UserService;
import com.vocation.travel.util.Utils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class SocketHandle extends TextWebSocketHandler {
  @Autowired
  private CRUD<ManagerSocketDTO, BaseResponse> managerSocketService;

  @Autowired
  private UserService userService;

  @RabbitListener(queues = "${rabbitmq.queue.name}")
  public void handleMessage(String message) {
    System.out.println("Socket handle");
    System.out.println(message);
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    System.out.println("After connection");
    ManagerSocketDTO managerSocketDto = new ManagerSocketDTO();
    managerSocketDto.setSessionId(session.getId());
    managerSocketDto.setUserId(userService.getIdUserByUserName());
    if (checkInputParams(managerSocketDto)) {
      session.close(CloseStatus.BAD_DATA);
    }
    managerSocketService.create(managerSocketDto);
    super.afterConnectionEstablished(session);
  }

  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    System.out.println("handleMessage");
    super.handleMessage(session, message);
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    System.out.println("handleTextMessage");
    super.handleTextMessage(session, message);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    System.out.println("afterConnectionClosed");
    super.afterConnectionClosed(session, status);
  }

  private boolean checkInputParams(ManagerSocketDTO managerSocketDto) {
    return Utils.objNull(managerSocketDto) || Utils.isEmpty(managerSocketDto.getSessionId())
        || Utils.isEmpty(managerSocketDto.getUserId());
  }
}