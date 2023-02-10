package com.cpnv.bijavaaws.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

public interface DataObject {
    boolean doesExist(String objectKey);

    void createObject(MultipartFile file, String objectKey) throws IOException;

    byte[] downloadObject(String key);

    URL publishObject(String key);

    void deleteObject(String key, boolean isRecursive);
}
