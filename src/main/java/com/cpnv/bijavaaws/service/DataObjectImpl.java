package com.cpnv.bijavaaws.service;

import com.cpnv.bijavaaws.exceptions.ObjectAlreadyExistsException;
import com.cpnv.bijavaaws.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

@Component
public class DataObjectImpl implements DataObject {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private S3Client s3Client;

    @Value("${AWS_BUCKET_NAME}")
    private String bucketName;

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
     * @param file the path of the object to be uploaded.
     * @throws ObjectAlreadyExistsException if the object already exists.
     */
    public void createObject(MultipartFile file) {
        String objectKey = file.getOriginalFilename();
        createObject(file, objectKey);
    }

    /**
     * Creates and store an object.
     *
     * @param file the path of the object to be uploaded.
     * @param objectKey  the path to the destination file.
     * @throws ObjectAlreadyExistsException if the object already exists.
     */
    @Override
    public void createObject(MultipartFile file, String objectKey)  {
        if (doesExist(objectKey))
            throw new ObjectAlreadyExistsException(objectKey);

        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();
        RequestBody requestBody = RequestBody.fromInputStream(inputStream, file.getSize());

        s3Client.putObject(putObjectRequest, requestBody);
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
     * @param key         the object key.
     * @param isRecursive if true, the object will be deleted recursively.
     */
    @Override
    public void deleteObject(String key, boolean isRecursive) {
        if (isRecursive) {
            var objects = s3Client.listObjects(builder -> builder.bucket(bucketName).prefix(key));
            objects.contents().forEach(s3Object -> s3Client.deleteObject(builder -> builder.bucket(bucketName).key(s3Object.key())));
        } else {
            if (!doesExist(key)) throw new ObjectNotFoundException(key);
            s3Client.deleteObject(builder -> builder.bucket(bucketName).key(key));
        }
    }

    /**
     * Deletes an object.
     *
     * @param key the object key.
     */
    public void deleteObject(String key) {
        deleteObject(key, false);
    }
}
