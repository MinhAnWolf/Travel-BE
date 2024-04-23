package com.vocation.travel.config;

import com.vocation.travel.security.FilterService;
import com.vocation.travel.socket.SocketHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocket
public class SocketConfig implements WebSocketConfigurer {
  @Autowired
  private FilterService filterService;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(getDataHandler(), "/friend")
        .addInterceptors((HandshakeInterceptor) filterService);
  }

  @Bean
  SocketHandle getDataHandler() {
    return new SocketHandle();
  }
}
