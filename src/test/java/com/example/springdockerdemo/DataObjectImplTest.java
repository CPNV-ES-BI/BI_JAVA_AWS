package com.example.springdockerdemo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataObjectImplTest {

    private DataObjectImpl dataObject;

    @BeforeEach
    void setUp() {
        dataObject = new DataObjectImpl();
    }

    @Test
    void doesExists_Exists_True() {
        // Given
        Object obj = new Object();

        // When
        boolean result = dataObject.doesExists(obj);

        // Then
        assertTrue(result);
    }

    @Test
    void DoesExist_NotExists_False() {
        // Given
        Object obj = new Object();

        // When
        boolean result = dataObject.doesExists(obj);

        // Then
        assertFalse(result);
    }

    @Test
    void CreateObject_NominalCase_Success() {
        // Given
        Object obj = new Object();

        // When
        dataObject.createObject(obj);

        // Then
        assertTrue(dataObject.doesExists(obj));
    }

    @Test
    void CreateObject_AlreadyExists_ThrowException() {
        // Given
        Object obj = new Object();
        dataObject.createObject(obj);

        // Then
        assertThrows(Exception.class, () -> {
            // When
            dataObject.createObject(obj);
        });
    }
}