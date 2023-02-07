package com.cpnv.bijavaaws.exception;

public class ObjectAlreadyExistsException extends RuntimeException {
    public ObjectAlreadyExistsException(String objectKey) {
        super(String.format("Object %s already exists", objectKey));
    }
}
