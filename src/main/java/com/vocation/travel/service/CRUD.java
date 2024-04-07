package com.vocation.travel.service;

import com.vocation.travel.model.BaseResponse;

public interface CRUD<E, T> {
    BaseResponse create(E request);
    BaseResponse read(E request);
    BaseResponse update(E request);
    BaseResponse delete(E request);

    T handleStartMethod(E request, String method);
}
