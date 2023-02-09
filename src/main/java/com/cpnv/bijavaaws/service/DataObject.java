package com.cpnv.bijavaaws.service;

import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

public interface DataObject {
    boolean doesExist(String objectKey);

    void createObject(MultipartFile file, String objectKey);

    byte[] downloadObject(String key);

    URL publishObject(String key);

    void deleteObject(String key, boolean isRecursive);
}
