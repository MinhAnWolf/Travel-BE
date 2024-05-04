package com.vocation.travel.notification.service;

import com.vocation.travel.common.Log;
import com.vocation.travel.config.ExceptionHandler;
import com.vocation.travel.config.Message;
import com.vocation.travel.model.Notification;
import com.vocation.travel.socket.MemorySocket;
import com.vocation.travel.socket.SocketHandle;
import com.vocation.travel.util.Utils;
import java.util.concurrent.CountDownLatch;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

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

  /**
   * Send message to msmq.
   *
   * @param jsonMessage String
   * */
  @RabbitListener(queues = "${rabbitmq.queue.name}")
  public void receiveNotification(String jsonMessage) {
    final String METHOD_NAME = "receiveNotification";
    Log.startLog(SERVICE_NAME, METHOD_NAME);
    Log.inputLog(jsonMessage);
    //Call deserialized
    Notification notification = Utils.deserializedJson(jsonMessage, Notification.class);
    try {
      for (String idUserReceive: notification.getReceiveUserId()) {
        WebSocketSession socketSession = MemorySocket.infoSocketUser.get(idUserReceive);
        System.out.println("Received <" + jsonMessage + ">");
        notification.setMessage(jsonMessage);
        socketHandle.handleTextMessage(socketSession, new TextMessage(notification.getMessage()));
      }
      Log.debugLog(notification);
      Log.endLog(SERVICE_NAME, METHOD_NAME);
    } catch (Exception e) {
      Log.errorLog(e.getMessage());
      Log.endLog(SERVICE_NAME, METHOD_NAME);
      throw new ExceptionHandler.SystemErrorException(getMessage("SystemErr"));
    }
    Log.endLog(SERVICE_NAME, METHOD_NAME);
    latch.countDown();
  }

  /**
   * Send message to msmq.
   *
   * @param notification Notification
   * */
  public void sendNotification(Notification notification) {
    final String METHOD_NAME = "sendNotification";
    Log.startLog(SERVICE_NAME, METHOD_NAME);
    Log.inputLog(notification);
    rabbitTemplate.convertAndSend(exChange, routing, Utils.deserializedObj(notification));
    Log.endLog(SERVICE_NAME, METHOD_NAME);
  }
}
