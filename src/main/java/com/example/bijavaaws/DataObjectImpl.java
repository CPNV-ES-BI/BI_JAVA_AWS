package com.example.bijavaaws;

import com.example.bijavaaws.exceptions.ObjectAlreadyExistsException;
import com.example.bijavaaws.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.nio.file.Path;
import java.util.List;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

@Component
public class DataObjectImpl implements DataObject {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private S3Client s3Client;

    @Value("${aws.bucketName}")
    private String bucketName;

    /**
     * List all buckets.
     *
     * @return List of buckets.
     */
    List<Bucket> listBuckets() {
        return s3Client.listBuckets().buckets();
    }

    /**
     * Checks if the object exists.
     *
     * @param objectKey the key of the object.
     * @return true if the object exists, false otherwise.
     */
    @Override
    public boolean doesExist(String objectKey) {
        try {
            s3Client.headObject(builder -> builder.bucket(bucketName).key(objectKey));
            return true;
        } catch (NoSuchKeyException e) {
            return false;
        }
    }

    /**
     * Creates and store an object. The future object key is the file name in the path.
     *
     * @param path the path to the file to be uploaded.
     * @throws ObjectAlreadyExistsException if the object already exists.
     */
    @Override
    public void createObject(Path path) {
        String objectKey = path.getFileName().toString();

        if (doesExist(objectKey))
            throw new ObjectAlreadyExistsException(objectKey);

        s3Client.putObject(builder -> builder.bucket(bucketName).key(objectKey).build(), path);
    }

    /**
     * Downloads an object.
     *
     * @param key the object key.
     * @return the object content.
     * @throws ObjectNotFoundException if the object does not exist.
     */
    @Override
    public byte[] downloadObject(String key) {
        if (!doesExist(key))
            throw new ObjectNotFoundException(key);

        return s3Client.getObjectAsBytes(builder -> builder.bucket(bucketName).key(key)).asByteArray();
    }

    /**
     * Makes an object public.
     *
     * @param key the object key.
     * @return the object public URL.
     * @throws ObjectNotFoundException if the object does not exist.
     */
    @Override
    public URL publishObject(String key) {
        if (!doesExist(key))
            throw new ObjectNotFoundException(key);

        PresignedGetObjectRequest presignedGetObjectRequest;
        try (S3Presigner s3Presigner = S3Presigner.create()) {
            presignedGetObjectRequest = s3Presigner.presignGetObject(presignBuilder -> presignBuilder
                    .signatureDuration(java.time.Duration.ofMinutes(5))
                    .getObjectRequest(getObjectBuilder -> getObjectBuilder.bucket(bucketName).key(key).build())
            );
        }

        return presignedGetObjectRequest.url();
    }

    /**
     * Deletes an object.
     *
     * @param key the object key.
     */
    public void deleteObject(String key) {
        deleteObject(key, false);
    }

    /**
     * Deletes an object.
     *
     * @param key         the object key.
     * @param isRecursive if true, the object will be deleted recursively.
     */
    public void deleteObject(String key, boolean isRecursive) {
        if (isRecursive)
            s3Client.listObjects(builder -> builder.bucket(bucketName).prefix(key))
                    .contents()
                    .forEach(s3Object -> s3Client.deleteObject(builder -> builder.bucket(bucketName).key(s3Object.key())));
        else {
            s3Client.deleteObject(builder -> builder.bucket(bucketName).key(key));
        }
    }
}
