package com.example.bijavaaws;

import java.net.URL;
import java.nio.file.Path;

public interface DataObject {
    boolean doesExist(String objectKey);

    void createObject(Path path);

    byte[] downloadObject(String key);

    URL publishObject(String key);
}
