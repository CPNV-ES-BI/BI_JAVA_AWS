package com.example.bijavaaws;

import java.nio.file.Path;

public interface DataObject {
    boolean doesExist(String objectKey);

    void createObject(Path path);

    byte[] downloadObject(String key);

    void publishObject(String key);
}
