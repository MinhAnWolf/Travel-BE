package com.vocation.travel.service;

public interface CRUD<E, T> {
    T create(E request);
    T read(E request);
    T update(E request);
    T delete(E request);
}
