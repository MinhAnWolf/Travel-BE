package com.vocation.travel.notification.service;

import java.util.concurrent.CountDownLatch;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
  @Value("${rabbitmq.exchange.name}")
  private String exChange;

  @Value("${rabbitmq.queue.json.key}")
  private String routingJsonKey;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  private final CountDownLatch latch = new CountDownLatch(1);

  @RabbitListener()
  public void receiveNotification(String message) {
    System.out.println("Received <" + message + ">");
    latch.countDown();
  }

  public void sendNotification(String exChange, String routing, String requestMessage) {
    rabbitTemplate.convertAndSend(exChange, routing, requestMessage);
  }
}
