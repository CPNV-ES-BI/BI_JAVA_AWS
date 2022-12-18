package com.example.bijavaaws;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import java.nio.file.Files;
import java.nio.file.Path;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BIJavaAWS.class)
class DataObjectImplTest {

    @Autowired
    private DataObjectImpl dataObject;

    private static Path testFilePath;
    private String objectKey;

    @BeforeAll
    static void beforeAll() throws FileNotFoundException {
        testFilePath = ResourceUtils.getFile("classpath:test-file.txt").toPath();
    }

    @BeforeEach
    void beforeEach() {
        dataObject.createObject(testFilePath);
        objectKey = testFilePath.getFileName().toString();
    }

    @AfterEach
    void afterEach() {
        dataObject.deleteObject(objectKey);
    }

    @Test
    void doesExist_ExistsCase_True() {
        // Given
        // createObject() is called in beforeEach()

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
        // createObject() is called in beforeEach()

        // Then
        assertTrue(dataObject.doesExist(objectKey));
    }

    @Test
    void createObject_AlreadyExists_ThrowException() {
        // Given
        // createObject() is called in beforeEach()

        // Then
        assertThrows(Exception.class, () -> {
            // When
            dataObject.createObject(testFilePath);
        });
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
        // createObject() is called in beforeEach()
        byte[] expectedContent = Files.readAllBytes(testFilePath);

        // When
        byte[] result = dataObject.downloadObject(objectKey);

        // Then
        assertEquals(expectedContent, result);
    }

    @Test
    void downloadObject_NotExists_ThrowException() {
        // Given
        String objectKey = "not-exists";

        // Then
        assertThrows(ObjectNotFoundException.class, () -> {
            // When
            dataObject.downloadObject(objectKey);
        });
    }

    @Test
    void publishObject_NominalCase_ObjectPublished() {
        // Given
        // createObject() is called in beforeEach()

        // When
        dataObject.publishObject(objectKey);

        // Then
        assertTrue(dataObject.isPublic(objectKey));
    }

    @Test
    void publishObject_ObjectNotFound_ThrowException() {
        // Given
        String objectKey = "not-exists";

        // Then
        assertThrows(ObjectNotFoundException.class, () -> {
            // When
            dataObject.publishObject(objectKey);
        });
    }
}
