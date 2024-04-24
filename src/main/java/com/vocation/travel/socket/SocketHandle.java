package com.vocation.travel.socket;

import com.vocation.travel.model.RequestMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class SocketHandle extends TextWebSocketHandler {
  @RabbitListener(queues = "${rabbitmq.queue.name}")
  public void handleMessage(RequestMessage requestMessage) {
    System.out.println("Socket handle");
    System.out.println(requestMessage);
  }
}
