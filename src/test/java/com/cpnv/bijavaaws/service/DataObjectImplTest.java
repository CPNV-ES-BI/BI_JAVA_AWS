package com.cpnv.bijavaaws.service;

import com.cpnv.bijavaaws.BIJavaAWSApplication;
import com.cpnv.bijavaaws.exception.ObjectAlreadyExistsException;
import com.cpnv.bijavaaws.exception.ObjectNotFoundException;
import com.cpnv.bijavaaws.config.ConfigMock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BIJavaAWSApplication.class, ConfigMock.class})
class DataObjectImplTest {

    @Autowired
    private DataObjectImpl dataObject;

    private static MultipartFile testFilePath;

    private String objectKey;

    @BeforeAll
    static void beforeAll() throws IOException {
        File file = ResourceUtils.getFile("classpath:data/test-file.txt");
        String originalFileName = file.getName();
        String contentType = "application/octet-stream";
        byte[] content = Files.readAllBytes(file.toPath());
        testFilePath = new MockMultipartFile(originalFileName, originalFileName, contentType, content);
    }

    @BeforeEach
    void beforeEach() throws IOException {
        objectKey = testFilePath.getOriginalFilename();
        deleteObjectIfExists(objectKey);

        dataObject.createObject(testFilePath);
    }

    @Test
    void doesExist_ExistsCase_True() {
        // When
        boolean result = dataObject.doesExist(objectKey);

        // Then
        assertTrue(result);
    }

    @Test
    void doesExist_NotExists_False() {
        // Given
        objectKey = "not-exists";

        // When
        boolean result = dataObject.doesExist(objectKey);

        // Then
        assertTrue(result);
    }

    @Test
    void createObject_NominalCase_ObjectExists() {
        // When
        // createObject is called in beforeEach()

        // Then
        assertTrue(dataObject.doesExist(objectKey));
    }

    @Test
    void createObject_AlreadyExists_ThrowException() {
        // When
        Callable<Void> createObject = () -> {
            dataObject.createObject(testFilePath);
            return null;
        };

        // Then
        assertThrows(ObjectAlreadyExistsException.class, createObject::call);
    }

    @Test
    void createObject_PathNotExists_ObjectExists() throws IOException {
        // Given
        objectKey = "level1/level2/level3/" + objectKey;
        deleteObjectIfExists(objectKey);

        // When
        dataObject.createObject(testFilePath, objectKey);

        // Then
        assertTrue(dataObject.doesExist(objectKey));
    }

    @Test
    void downloadObject_NominalCase_Downloaded() throws IOException {
        // Given
        String expectedContent = new String(testFilePath.getBytes());

        // When
        byte[] result = dataObject.downloadObject(objectKey);

        // Then
        assertEquals(expectedContent, new String(result));
    }

    @Test
    void downloadObject_NotExists_ThrowException() {
        // Given
        objectKey = "not-exists";

        // When
        Callable<Void> downloadObject = () -> {
            dataObject.downloadObject(objectKey);
            return null;
        };

        // Then
        assertThrows(ObjectNotFoundException.class, downloadObject::call);
    }

    @Test
    void publishObject_NominalCase_ObjectPublished() throws IOException {
        // Given
        String expectedContent = new String(testFilePath.getBytes());

        // When
        URL objectUrl = dataObject.publishObject(objectKey);

        // Then
        byte[] objectContent = objectUrl.openConnection().getInputStream().readAllBytes();
        assertEquals(expectedContent, new String(objectContent));
    }

    @Test
    void publishObject_ObjectNotFound_ThrowException() {
        // Given
        objectKey = "not-exists";

        // When
        Callable<Void> publishObject = () -> {
            dataObject.publishObject(objectKey);
            return null;
        };

        // Then
        assertThrows(ObjectNotFoundException.class, publishObject::call);
    }

    @Test
    void deleteObject_ObjectExists_ObjectDeleted() {
        // When
        dataObject.deleteObject(objectKey);

        // Then
        assertFalse(dataObject.doesExist(objectKey));
    }

    @Test
    void deleteObject_ObjectContainingSubObjectsExists_ObjectDeletedRecursively() throws IOException {
        // Given
        String keyToDelete = "level1";
        objectKey = MessageFormat.format("{0}/level2/level3/{1}", keyToDelete, objectKey);
        deleteObjectIfExists(objectKey);
        dataObject.createObject(testFilePath, objectKey);
        boolean isRecursive = true;

        // When
        dataObject.deleteObject(keyToDelete, isRecursive);

        // Then
        assertFalse(dataObject.doesExist(objectKey));
    }

    @Test
    void deleteObject_ObjectDoesntExist_ThrowException() {
        // Given
        objectKey = "not-exists";

        // When
        Callable<Void> deleteObject = () -> {
            dataObject.deleteObject(objectKey);
            return null;
        };

        // Then
        assertThrows(ObjectNotFoundException.class, deleteObject::call);
    }

    private void deleteObjectIfExists(String objectKey) {
        if (dataObject.doesExist(objectKey))
            dataObject.deleteObject(objectKey);
    }
}
