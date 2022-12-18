package com.example.bijavaaws.exceptions;

public class ObjectAlreadyExistsException extends RuntimeException {
    public ObjectAlreadyExistsException(String objectKey) {
        super(String.format("Object %s already exists", objectKey));
    }
}
