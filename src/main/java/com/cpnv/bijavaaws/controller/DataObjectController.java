package com.cpnv.bijavaaws.controller;

import com.cpnv.bijavaaws.service.DataObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.net.URL;

@Api("DataObject API REST")

@RestController
@Validated
@RequestMapping("/api")
public class DataObjectController {

    @Autowired
    private final DataObject dataObject;

    public DataObjectController(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    @ApiOperation(value = "Create an object in the S3 bucket")
    @PostMapping("/objects")
    public ResponseEntity<String> createObject(
            @RequestParam("file") @NotNull MultipartFile file,
            @RequestParam(name = "key") @NotEmpty @NotBlank @NotNull String key
    ) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        dataObject.createObject(file, key + "." + extension);
        return ResponseEntity.status(HttpStatus.CREATED).body("Object created successfully");
    }

    @ApiOperation(value = "Download an object from the S3 bucket")
    @GetMapping("/objects/{key}")
    public ResponseEntity<byte[]> downloadObject(
            @PathVariable String key,
            @RequestHeader(HttpHeaders.CONTENT_TYPE) @NotNull @NotEmpty String contentType
    ) throws MimeTypeException {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + this.getFullFileName(key, contentType))
                .body(dataObject.downloadObject(this.getFullFileName(key, contentType)));
    }

    @ApiOperation(value = "Delete an object from the S3 bucket")
    @DeleteMapping("/objects/{key}")
    public ResponseEntity<String> deleteObject(
            @PathVariable String key,
            @RequestParam(name = "recursive", required = false) boolean recursive,
            @RequestHeader(HttpHeaders.CONTENT_TYPE) @NotNull @NotEmpty String contentType
    ) throws MimeTypeException {
        dataObject.deleteObject(this.getFullFileName(key, contentType), recursive);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Object deleted successfully");
    }

    @ApiOperation(value = "Publish an object from the S3 bucket")
    @PatchMapping("/objects/{key}/publish")
    public ResponseEntity<URL> publishObject(
            @ApiParam(value = "Value of the object key") @PathVariable String key,
            @RequestHeader(HttpHeaders.CONTENT_TYPE) @NotNull @NotEmpty String contentType
    ) throws MimeTypeException {
        return ResponseEntity.status(HttpStatus.OK).body(dataObject.publishObject(this.getFullFileName(key, contentType)));
    }

    private String getFullFileName(String key, String contentType) throws MimeTypeException {
        MimeType mimeType = MimeTypes.getDefaultMimeTypes().forName(contentType);
        return key + mimeType.getExtension();
    }
}
