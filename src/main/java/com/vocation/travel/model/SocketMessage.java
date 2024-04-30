package com.vocation.travel.model;

import org.springframework.web.socket.WebSocketMessage;

public class SocketMessage implements WebSocketMessage<BaseResponse> {
    private BaseResponse message;
    @Override
    public BaseResponse getPayload() {
        return message;
    }

    @Override
    public int getPayloadLength() {
        return 0;
    }

    @Override
    public boolean isLast() {
        return false;
    }
}
