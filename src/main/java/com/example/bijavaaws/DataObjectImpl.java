package com.example.bijavaaws;

import com.example.bijavaaws.exceptions.ObjectAlreadyExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

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
    public boolean doesExist(Path objectPath) {
        try {
            s3Client.headObject(builder -> builder.bucket(bucketName).key(getObjectName(objectPath)));
            return true;
        } catch (NoSuchKeyException e) {
            if (e.statusCode() == 404)
                return false;
            throw e;
        }
    }

    @Override
    public void createObject(Path objectPath) {
        String objectName = getObjectName(objectPath);

        if (doesExist(objectPath))
            throw new ObjectAlreadyExistsException(objectName);

        s3Client.putObject(builder -> builder.bucket(bucketName).key(objectName).build(), objectPath);
    }

    @Override
    public Object downloadObject(String path) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void publishObject(Object obj) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private String getObjectName(Path objectPath) {
        return objectPath.getFileName().toString();
    }
}
