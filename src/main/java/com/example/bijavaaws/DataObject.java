package com.example.bijavaaws;

import java.nio.file.Path;

public interface DataObject {
    boolean doesExist(Path sourcePath);

    void createObject(Path sourcePath);

    Object downloadObject(String path);

    void publishObject(Object obj);
}
