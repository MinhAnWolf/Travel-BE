package com.vocation.travel.socket;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class SocketHandle extends TextWebSocketHandler {
  @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
  public void handleMessage(RequestMessage requestMessage) {

  }
}
