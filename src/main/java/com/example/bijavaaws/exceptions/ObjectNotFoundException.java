package com.example.bijavaaws.exceptions;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String objectName) {
        super(String.format("Object %s not found", objectName));
    }
}
