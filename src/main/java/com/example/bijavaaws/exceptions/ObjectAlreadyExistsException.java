package com.example.bijavaaws.exceptions;

public class ObjectAlreadyExistsException extends RuntimeException {
    public ObjectAlreadyExistsException(String objectName) {
        super(String.format("Object %s already exists", objectName));
    }
}
