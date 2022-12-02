package com.example.springdockerdemo;

public interface DataObject {
    boolean doesExists(Object obj);

    void createObject(Object obj);

    Object downloadObject(String path);

    void publishObject();
}
