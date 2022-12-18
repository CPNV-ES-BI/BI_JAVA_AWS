package com.example.bijavaaws;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.example.bijavaaws.exceptions.ObjectAlreadyExistsException;
import com.example.bijavaaws.exceptions.ObjectNotFoundException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BIJavaAWS.class)
class DataObjectImplTest {

    @Autowired
    private DataObjectImpl dataObject;

    private static Path testFilePath;

    @BeforeAll
    static void beforeAll() throws FileNotFoundException {
        testFilePath = ResourceUtils.getFile("classpath:test-file.txt").toPath();
    }

    @AfterEach
    void afterEach() {
        // TODO : Remove the test file from the bucket
    }

    @Test
    void doesExist_ExistsCase_True() {
        // Given
        dataObject.createObject(testFilePath);

        // When
        boolean result = dataObject.doesExist(testFilePath);

        // Then
        assertTrue(result);
    }

    @Test
    void doesExist_NotExists_False() {
        // Given
        Path notExistsPath = Path.of("not-exists");

        // When
        boolean result = dataObject.doesExist(testFilePath);

        // Then
        assertFalse(result);
    }

    @Test
    void createObject_NominalCase_ObjectExists() throws IOException {
        // When
        dataObject.createObject(testFilePath);

        // Then
        assertTrue(dataObject.doesExist(testFilePath));
    }

    @Test
    void createObject_AlreadyExists_ThrowException() throws FileNotFoundException {
        dataObject.createObject(testFilePath);

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
        assertTrue(dataObject.doesExist(testFilePath));
    }

    @Test
    void downloadObject_NominalCase_Downloaded() {
        // Given
        Path sourcePath = Path.of("not-exists");
        dataObject.createObject(sourcePath);
        String path = "path";

        // When
        Object result = dataObject.downloadObject(path);

        // Then
        assertNotNull(result);
    }

    @Test
    void downloadObject_NotExists_ThrowException() {
        // Given
        String path = "not-existing-path";

        // Then
        assertThrows(ObjectNotFoundException.class, () -> {
            // When
            dataObject.downloadObject(path);
        });
    }

    @Test
    void publishObject_NominalCase_ObjectPublished() {
        // Given
        Object obj = new Object();

        // When
        dataObject.publishObject(obj);

        // Then
        fail();
    }

    @Test
    void publishObject_ObjectNotFound_ThrowException() {
        // Given
        Object obj = new Object();

        // Then
        assertThrows(ObjectAlreadyExistsException.class, () -> {
            // When
            dataObject.publishObject(obj);
        });
    }
}
