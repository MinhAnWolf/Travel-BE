package com.vocation.travel.notification.service;

import com.vocation.travel.common.Log;
import com.vocation.travel.common.constant.CommonConstant;
import com.vocation.travel.config.ExceptionHandler;
import com.vocation.travel.config.Message;
import com.vocation.travel.model.RequestMessage;
import com.vocation.travel.model.Socket;
import com.vocation.travel.model.SocketMessage;
import com.vocation.travel.socket.SocketHandle;
import java.util.concurrent.CountDownLatch;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketMessage;

@Service
public class NotificationService extends Message {
  @Value("${rabbitmq.exchange.name}")
  private String exChange;

//  @Value("${rabbitmq.queue.json.key}")
//  private String routingJsonKey;

  @Value("${rabbitmq.key.name}")
  private String routing;

  private final String SERVICE_NAME = "NotificationService";

  @Autowired
  private SocketHandle socketHandle;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  private final CountDownLatch latch = new CountDownLatch(1);

  @RabbitListener(queues = "${rabbitmq.queue.name}")
  public void receiveNotification(String message, RequestMessage requestMessage) {
    final String METHOD_NAME = "receiveNotification";
    Log.startLog(SERVICE_NAME, METHOD_NAME);
    Log.inputLog(message);
    Log.inputLog(requestMessage);
    try {
      for (String idSession: requestMessage.getClientReceives()) {
        System.out.println("Received <" + message + ">");
        requestMessage.setMessage(message);
        WebSocketMessage<?> socketMessage = new SocketMessage();
        Socket session = new Socket(idSession);
        socketHandle.handleMessage(session, socketMessage);
      }
      Log.endLog(SERVICE_NAME, METHOD_NAME);
    } catch (Exception e) {
      Log.errorLog(e.getMessage());
      Log.endLog(SERVICE_NAME, METHOD_NAME);
      throw new ExceptionHandler.SystemErrorException(getMessage("SystemErr"));
    }
    Log.endLog(SERVICE_NAME, METHOD_NAME);
    latch.countDown();
  }

  public void sendNotification(String requestMessage) {
    rabbitTemplate.convertAndSend(exChange, routing, requestMessage);
  }
}
