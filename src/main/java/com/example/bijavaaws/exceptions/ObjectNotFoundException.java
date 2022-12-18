package com.example.bijavaaws.exceptions;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String objectKEy) {
        super(String.format("Object %s not found", objectKEy));
    }
}
