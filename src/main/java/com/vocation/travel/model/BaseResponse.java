package com.vocation.travel.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {
    private int errCode;
    private Object data;
    private String message;
}
