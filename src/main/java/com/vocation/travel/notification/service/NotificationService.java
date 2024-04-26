package com.vocation.travel.notification.service;

import com.vocation.travel.model.RequestMessage;
import com.vocation.travel.model.WebSocket;
import com.vocation.travel.socket.SocketHandle;
import java.util.concurrent.CountDownLatch;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Service
public class NotificationService {
  @Value("${rabbitmq.exchange.name}")
  private String exChange;

//  @Value("${rabbitmq.queue.json.key}")
//  private String routingJsonKey;

  @Value("${rabbitmq.key.name}")
  private String routing;

  @Autowired
  private SocketHandle socketHandle;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  private final CountDownLatch latch = new CountDownLatch(1);

  @RabbitListener(queues = "${rabbitmq.queue.name}")
  public void receiveNotification(RequestMessage requestMessage) {
    for ()
    System.out.println("Received <" + message + ">");
    WebSocket webSocket = new WebSocket(requestMessage.getClientReceives());
    socketHandle.handleMessage(WebSocket session, WebSocket.);
    latch.countDown();
  }

  public void sendNotification(String requestMessage) {
    rabbitTemplate.convertAndSend(exChange, routing, requestMessage);
  }
}
