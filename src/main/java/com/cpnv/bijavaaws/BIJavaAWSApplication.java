package com.cpnv.bijavaaws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class BIJavaAWSApplication {

    public static void main(String[] args) {
        // Allow encoded slashes in URL
        System.setProperty("org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH", "true");
        SpringApplication.run(BIJavaAWSApplication.class, args);
    }
}
