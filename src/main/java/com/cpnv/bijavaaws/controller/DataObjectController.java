package com.cpnv.bijavaaws.controller;

import com.cpnv.bijavaaws.annotation.NonEmptyString;
import com.cpnv.bijavaaws.service.DataObject;
import com.cpnv.bijavaaws.service.ExtensionResolver;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tika.mime.MimeTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URL;

@Api("DataObject API REST")

@RestController
@Validated
@RequestMapping("/api")
public class DataObjectController {

    @Autowired
    private final DataObject dataObject;

    @Autowired
    private final ExtensionResolver extensionResolver;

    public DataObjectController(DataObject dataObject, ExtensionResolver extensionResolver) {
        this.dataObject = dataObject;
        this.extensionResolver = extensionResolver;
    }

    @ApiOperation(value = "Create an object in the S3 bucket")
    @PostMapping("/objects")
    public ResponseEntity<String> create(
            @RequestParam(name = "file") @NotNull MultipartFile file,
            @RequestParam(name = "key") @NonEmptyString String key
    ) throws MimeTypeException, IOException {
        dataObject.createObject(file, extensionResolver.extractMultipartFileDetails(key, file));
        return ResponseEntity.status(HttpStatus.CREATED).body("Object created successfully");
    }

    @ApiOperation(value = "Download an object from the S3 bucket")
    @GetMapping("/objects/{key}")
    public ResponseEntity<byte[]> download(
            @PathVariable String key,
            @RequestHeader(HttpHeaders.CONTENT_TYPE) @NonEmptyString String contentType
    ) throws MimeTypeException {
        String fullFileName = extensionResolver.extractContentTypeDetails(key, contentType);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fullFileName)
                .body(dataObject.downloadObject(fullFileName));
    }

    @ApiOperation(value = "Delete an object from the S3 bucket")
    @DeleteMapping("/objects/{key}")
    public ResponseEntity<String> delete(
            @PathVariable String key,
            @RequestParam(name = "recursive", required = false) boolean recursive,
            @RequestHeader(value = HttpHeaders.CONTENT_TYPE, required = false) String contentType
    ) throws MimeTypeException {
        String fullFileName = recursive ? key : extensionResolver.extractContentTypeDetails(key, contentType);
        dataObject.deleteObject(fullFileName, recursive);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Publish an object from the S3 bucket")
    @PatchMapping("/objects/{key}/publish")
    public ResponseEntity<URL> publish(
            @PathVariable String key,
            @RequestHeader(HttpHeaders.CONTENT_TYPE) @NonEmptyString String contentType
    ) throws MimeTypeException {
        return ResponseEntity.ok(dataObject.publishObject(extensionResolver.extractContentTypeDetails(key, contentType)));
    }
}
