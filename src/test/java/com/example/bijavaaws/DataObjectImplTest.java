package com.example.bijavaaws;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BIJavaAWS.class)
class DataObjectImplTest {

    @Autowired
    private DataObjectImpl dataObject;

    @Test
    void doesExist_ExistsCase_True() {
        // Given
        Object obj = new Object();

        // When
        boolean result = dataObject.doesExists(obj);

        // Then
        assertTrue(result);
    }

    @Test
    void doesExist_NotExists_False() {
        // Given
        Object obj = new Object();

        // When
        boolean result = dataObject.doesExists(obj);

        // Then
        assertFalse(result);
    }

    @Test
    void createObject_NominalCase_ObjectExists() {
        // Given
        Object obj = new Object();

        // When
        dataObject.createObject(obj);

        // Then
        assertTrue(dataObject.doesExists(obj));
    }

    @Test
    void createObject_AlreadyExists_ThrowException() {
        // Given
        Object obj = new Object();
        dataObject.createObject(obj);

        // Then
        assertThrows(Exception.class, () -> {
            // When
            dataObject.createObject(obj);
        });
    }

    @Test
    void createObject_PathNotExists_ObjectExists() {
        // Given
        Object obj = new Object();

        // When
        dataObject.createObject(obj);

        // Then
        assertTrue(dataObject.doesExists(obj));
    }

    @Test
    void downloadObject_NominalCase_Downloaded() {
        // Given
        Object obj = new Object();
        dataObject.createObject(obj);
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
        assertThrows(Exception.class, () -> {
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
    }

    @Test
    void publishObject_ObjectNotFound_ThrowException() {
        // Given
        Object obj = new Object();

        // Then
        assertThrows(Exception.class, () -> {
            // When
            dataObject.publishObject(obj);
        });
    }
}