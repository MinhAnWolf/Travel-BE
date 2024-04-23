package com.vocation.travel.config;

import org.apache.qpid.proton.engine.Receiver;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {
  @Value("${rabbitmq.queue.name}")
  private String queue;

  @Value("${rabbitmq.queue.json.name}")
  private String jsonQueue;

  @Value("${rabbitmq.exchange.name}")
  private String exchange;

  @Value("${rabbitmq.key.name}")
  private String routingKey;

  @Value("${rabbitmq.queue.json.key}")
  private String jsonRoutingKey;

  @Bean
  public Queue queue() {
    return new Queue(queue);
  }

  @Bean
  public Queue queueJson() {
    return new Queue(jsonQueue);
  }
  @Bean
  public TopicExchange exchange() {
    return new TopicExchange(exchange);
  }

  @Bean
  public Binding binding() {
    return BindingBuilder
        .bind(queue())
        .to(exchange())
        .with(routingKey);
  }

  @Bean
  public Binding jsonBinding() {
    return BindingBuilder
        .bind(queueJson())
        .to(exchange())
        .with(jsonRoutingKey);
  }

  @Bean
  public MessageConverter converter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(converter());
    return rabbitTemplate;
  }

//  @Bean
//  MessageListenerAdapter listenerAdapter(Receiver receiver) {
//    return new MessageListenerAdapter(receiver, "receiveMessage");
//  }

//  @Bean
//  SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
//                                           MessageListenerAdapter listenerAdapter) {
//    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//    container.setConnectionFactory(connectionFactory);
//    container.setQueueNames(jsonQueue);
//    container.setMessageListener(listenerAdapter);
//    return container;
//  }
}
