package com.example.bijavaaws;

import java.nio.file.Path;

public interface DataObject {
    boolean doesExist(Path objectPath);

    void createObject(Path path);

    byte[] downloadObject(Path path);

    void publishObject(Object obj);
}
