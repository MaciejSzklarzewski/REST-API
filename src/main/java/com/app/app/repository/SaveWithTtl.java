package com.app.app.repository;


public interface SaveWithTtl<T> {

    T save(T message, int ttl);
}