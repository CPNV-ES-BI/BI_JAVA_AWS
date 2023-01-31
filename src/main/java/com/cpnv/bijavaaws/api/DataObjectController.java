package com.cpnv.bijavaaws.api;

import com.cpnv.bijavaaws.dataobject.DataObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class DataObjectController {

    @Autowired
    private final DataObject dataObject;

    public DataObjectController(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    @PostMapping("/objects")
    public ResponseEntity<String> createObject(
            @RequestParam("file") MultipartFile file,
            @RequestParam(name = "key", required = false) String key
    ){
        if (key == null || key.isEmpty()) {
            key = file.getOriginalFilename();
        }
        dataObject.createObject(file, key);
        return ResponseEntity.status(HttpStatus.CREATED).body("Object created successfully");
    }

    @GetMapping("/objects/{objectKey}")
    public ResponseEntity<byte[]> downloadObject(@PathVariable String objectKey) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + objectKey)
                .body(dataObject.downloadObject(objectKey));
    }

    @DeleteMapping("/objects/{objectKey}")
    public ResponseEntity<String> deleteObject(@PathVariable String objectKey, @RequestParam(name = "recursive", required = false) boolean recursive) {

        dataObject.deleteObject(objectKey, recursive);
        return ResponseEntity.status(HttpStatus.OK).body("Object deleted successfully");
    }

    @PatchMapping("/objects/{objectKey}")
    public ResponseEntity<String> publishObject(@PathVariable String objectKey) {
        return ResponseEntity.status(HttpStatus.OK).body("Object published successfully: " + dataObject.publishObject(objectKey));
    }
}
