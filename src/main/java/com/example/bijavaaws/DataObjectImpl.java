package com.example.bijavaaws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.PostConstruct;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;

@Component
public class DataObjectImpl implements DataObject {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private S3Client s3Client;

    /**
     * Because application properties  are not available in the constructor,
     * we need to use the @PostConstruct annotation.
     */
    @PostConstruct
    public void init() {
        s3Client.listBuckets().buckets().forEach(System.out::println);
    }

    List<Bucket> listBuckets() {
        return s3Client.listBuckets().buckets();
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
