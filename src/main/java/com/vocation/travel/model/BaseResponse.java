package com.vocation.travel.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class BaseResponse {
    private int errCode;
    private Object data;
    private String message;

    public BaseResponse(int errCode, Object data, String message) {
        this.errCode = errCode;
        this.data = data;
        this.message = message;
    }
}
