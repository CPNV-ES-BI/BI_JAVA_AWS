package com.example.bijavaaws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import software.amazon.awssdk.services.s3.model.Bucket;

@SpringBootApplication
@RestController
@PropertySource("classpath:aws.properties")
public class BIJavaAWS {

    private final DataObjectImpl dataObject;

    public BIJavaAWS(DataObjectImpl dataObject) {
        this.dataObject = dataObject;
    }

    public static void main(String[] args) {
        SpringApplication.run(BIJavaAWS.class, args);
    }

    @GetMapping("/")
    public String home() {
        return "Hello World !";
    }

    @GetMapping("/buckets")
    public List<Bucket.Builder> getBuckets() {
        return dataObject.listBuckets().stream().map(Bucket::toBuilder).toList();
    }
}
