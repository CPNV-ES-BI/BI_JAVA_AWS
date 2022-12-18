package com.example.bijavaaws;

import com.example.bijavaaws.exceptions.ObjectAlreadyExistsException;
import com.example.bijavaaws.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.GetObjectAclResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.Permission;
import software.amazon.awssdk.services.s3.model.PutObjectAclRequest;
import software.amazon.awssdk.services.s3.model.Type;

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
     * @throws ObjectNotFoundException if the object does not exist.
     */
    @Override
    public void publishObject(String key) {
        if (!doesExist(key))
            throw new ObjectNotFoundException(key);

        PutObjectAclRequest putRequest = PutObjectAclRequest.builder()
                .bucket(bucketName)
                .key(key)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        s3Client.putObjectAcl(putRequest);
    }

    /**
     * Tells if an object is public.
     *
     * @param objectKey the object key.
     * @return true if the object is public, false otherwise.
     */
    public boolean isPublic(String objectKey) {
        GetObjectAclResponse response = s3Client.getObjectAcl(builder -> builder.bucket(bucketName).key(objectKey));

        return response.grants().stream()
                .anyMatch(grant -> grant.permission().equals(Permission.READ)
                        && grant.grantee().type().equals(Type.GROUP)
                        && grant.grantee().uri().equals("http://acs.amazonaws.com/groups/global/AllUsers"));
    }

    /**
     * Deletes an object.
     *
     * @param key the object key.
     */
    public void deleteObject(String key) {
        s3Client.deleteObject(builder -> builder.bucket(bucketName).key(key));
    }
}
