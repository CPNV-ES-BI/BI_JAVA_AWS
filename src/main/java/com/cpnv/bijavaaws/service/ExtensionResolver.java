package com.cpnv.bijavaaws.service;

import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ExtensionResolver {
    public String extractContentTypeDetails(String key, String contentType) throws MimeTypeException {
        MimeType mimeType = MimeTypes.getDefaultMimeTypes().forName(contentType);
        return key + mimeType.getExtension();
    }

    public String extractMultipartFileDetails(String key, MultipartFile file) throws MimeTypeException {
        MimeType mimeType = MimeTypes.getDefaultMimeTypes().forName(file.getContentType());
        return key + mimeType.getExtension();
    }
}
