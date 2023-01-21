package com.example.bijavaaws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@PropertySource("classpath:aws.properties")
public class BIJavaAWSApplication {

    public static void main(String[] args) {
        SpringApplication.run(BIJavaAWSApplication.class, args);
    }
}
