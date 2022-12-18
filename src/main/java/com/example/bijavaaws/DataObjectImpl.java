package com.example.bijavaaws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;

@Component
public class DataObjectImpl implements DataObject {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private S3Client s3Client;

    @Value("${aws.bucketName}")
    private String bucketName;

    List<Bucket> listBuckets() {
        return s3Client.listBuckets().buckets();
    }

    @Override
    public boolean doesExist() {
        try {
            s3Client.headBucket(builder -> builder.bucket(bucketName));
            return true;
        } catch (NoSuchBucketException e) {
            return false;
        }
    }

    @Override
    public void createObject(Path sourcePath) {
        s3Client.putObject(
                builder -> builder.bucket(bucketName).key(sourcePath.getFileName().toString()),
                sourcePath
        );
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
