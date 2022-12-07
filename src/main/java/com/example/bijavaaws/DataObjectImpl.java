package com.example.bijavaaws;

import software.amazon.awssdk.services.s3.S3Client;

public class DataObjectImpl implements DataObject {

    private final S3Client s3;

    public DataObjectImpl() {
        s3 = S3Client.create();
    }

    @Override
    public boolean doesExists(Object obj) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void createObject(Object obj) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Object downloadObject(String path) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void publishObject(Object obj) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
