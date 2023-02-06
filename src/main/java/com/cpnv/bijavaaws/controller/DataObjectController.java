package com.cpnv.bijavaaws.controller;

import com.cpnv.bijavaaws.exceptions.ObjectAlreadyExistsException;
import com.cpnv.bijavaaws.exceptions.ObjectNotFoundException;
import com.cpnv.bijavaaws.service.DataObject;
import io.swagger.annotations.*;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

@Api("DataObject API REST")

@RestController
@RequestMapping("/api")
public class DataObjectController {

    @Autowired
    private final DataObject dataObject;

    @Autowired
    private Tika tika;

    public DataObjectController(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    @ApiOperation(value = "Create an object in the S3 bucket")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Object created successfully"),
            @ApiResponse(code = 409, message = "Object already exists", response = ObjectAlreadyExistsException.class)
    })
    @PostMapping("/objects")
    public ResponseEntity<String> createObject(
            @ApiParam(value = "File to upload", required = true, type = "file")
            @RequestParam("file") MultipartFile file,
            @ApiParam(value = "Value of the object key")
            @RequestParam(name = "key") String key
    ) {
        dataObject.createObject(file, key + "." + tika.detect(file.getContentType()));
        return ResponseEntity.status(HttpStatus.CREATED).body("Object created successfully");
    }

    @ApiOperation(value = "Download an object from the S3 bucket")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Object downloaded successfully", responseHeaders = {
                    @ResponseHeader(name = "Content-Type", description = "The content type of the object", response = String.class),
                    @ResponseHeader(name = "Content-Disposition", description = "The content disposition of the object", response = String.class)}),
            @ApiResponse(code = 404, message = "Object not found", response = ObjectNotFoundException.class)})
    @GetMapping("/objects/{key}")
    public ResponseEntity<byte[]> downloadObject(
            @ApiParam(value = "Value of the object key", required = true) @PathVariable String key,
            @RequestHeader(HttpHeaders.CONTENT_TYPE) String contentType
    ) {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + this.getFileWithExtension(key, contentType))
                .body(dataObject.downloadObject(key));
    }

    @ApiOperation(value = "Delete an object from the S3 bucket")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Object deleted successfully"),
            @ApiResponse(code = 404, message = "Object not found", response = ObjectNotFoundException.class)
    })
    @DeleteMapping("/objects/{key}")
    public ResponseEntity<String> deleteObject(
            @ApiParam(value = "Value of the object key")
            @PathVariable String key,
            @ApiParam(value = "If true, the object will be deleted recursively. If false, the object will be deleted only if it is empty.", required = false)
            @RequestParam(name = "recursive", required = false) boolean recursive,
            @RequestHeader(HttpHeaders.CONTENT_TYPE) String contentType
    ) {
        dataObject.deleteObject(this.getFileWithExtension(key, contentType), recursive);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Object deleted successfully");
    }

    @ApiOperation(value = "Publish an object from the S3 bucket")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Object published successfully", response = URL.class, responseHeaders = {@ResponseHeader(name = "Location", description = "URL of the published object", response = URL.class)}),
            @ApiResponse(code = 404, message = "Object not found", response = ObjectNotFoundException.class)
    })
    @PatchMapping("/objects/{key}/publish")
    public ResponseEntity<URL> publishObject(
            @ApiParam(value = "Value of the object key") @PathVariable String key,
            @RequestHeader(HttpHeaders.CONTENT_TYPE) String contentType
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(dataObject.publishObject(this.getFileWithExtension(key, contentType)));
    }

    private String getFileWithExtension(String key, String contentType) {

        return key + "." + tika.detect(contentType);
    }
}