package com.example.bijavaaws;

public interface DataObject {
    boolean doesExist();

    void createObject(Object obj);

    Object downloadObject(String path);

    void publishObject(Object obj);
}
