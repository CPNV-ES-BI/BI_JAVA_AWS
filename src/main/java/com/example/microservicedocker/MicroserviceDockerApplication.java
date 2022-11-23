package com.example.microservicedocker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MicroserviceDockerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceDockerApplication.class, args);
    }

    @GetMapping("/")
    public String home() {
        return "Hello World";
    }
}
