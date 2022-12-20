package com.example.bijavaaws;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.bijavaaws.exceptions.ObjectAlreadyExistsException;
import com.example.bijavaaws.exceptions.ObjectNotFoundException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BIJavaAWS.class)
class DataObjectImplTest {

    private static Path testFilePath;
    private static String objectKey;

    @Autowired
    private DataObjectImpl dataObject;

    @BeforeAll
    static void beforeAll() throws FileNotFoundException {
        testFilePath = ResourceUtils.getFile("classpath:test-file.txt").toPath();
        objectKey = testFilePath.getFileName().toString();
    }

    @BeforeEach
    void beforeEach() {
        if (dataObject.doesExist(objectKey))
            dataObject.deleteObject(objectKey);

        dataObject.createObject(testFilePath);
    }

    @AfterEach
    void afterEach() {
        dataObject.deleteObject(objectKey);
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
        String objectKey = "not-exists";

        // When
        boolean result = dataObject.doesExist(objectKey);

        // Then
        assertFalse(result);
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
    void createObject_PathNotExists_ObjectExists() {
        // Given
        Path sourcePath = Path.of("not-exists");

        // When
        dataObject.createObject(sourcePath);

        // Then
        assertTrue(dataObject.doesExist(objectKey));
    }

    @Test
    void downloadObject_NominalCase_Downloaded() throws IOException {
        // Given
        String expectedContent = Files.readString(testFilePath);

        // When
        byte[] result = dataObject.downloadObject(objectKey);

        // Then
        assertEquals(expectedContent, new String(result));
    }

    @Test
    void downloadObject_NotExists_ThrowException() {
        // Given
        String objectKey = "not-exists";

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
        String expectedContent = Files.readString(testFilePath);

        // When
        URL objectUrl = dataObject.publishObject(objectKey);

        // Then
        byte[] objectContent = objectUrl.openConnection().getInputStream().readAllBytes();
        assertEquals(expectedContent, new String(objectContent));
    }

    @Test
    void publishObject_ObjectNotFound_ThrowException() {
        // Given
        String objectKey = "not-exists";

        // When
        Callable<Void> publishObject = () -> {
            dataObject.publishObject(objectKey);
            return null;
        };

        // Then
        assertThrows(ObjectNotFoundException.class, publishObject::call);
    }
}
