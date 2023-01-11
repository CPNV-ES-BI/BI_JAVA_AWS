package com.example.bijavaaws.dataobject;

import java.net.URL;
import java.nio.file.Path;

public interface DataObject {
    boolean doesExist(String objectKey);

    void createObject(Path sourcePath, String objectKey);

    byte[] downloadObject(String key);

    URL publishObject(String key);

    void deleteObject(String key, boolean isRecursive);
}
