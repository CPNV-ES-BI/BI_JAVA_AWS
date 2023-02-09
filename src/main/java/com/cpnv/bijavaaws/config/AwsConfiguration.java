package com.cpnv.bijavaaws.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import io.awspring.cloud.core.region.StaticRegionProvider;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.providers.AwsRegionProvider;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
@PropertySource("classpath:aws.properties")
@PropertySource(value = "classpath:aws.secrets.properties", ignoreResourceNotFound = true)
class AwsConfiguration {

    @Value("${AWS_REGION}")
    private String region;

    @Value("${AWS_ACCESS_KEY_ID}")
    private String accessKeyId;

    @Value("${AWS_SECRET_ACCESS_KEY}")
    private String secretAccessKey;

    @Bean
    AwsRegionProvider awsRegionProvider() {
        return new StaticRegionProvider(region);
    }

    @Bean
    AwsCredentialsProvider awsCredentialsProvider() {
        return StaticCredentialsProvider.create(awsCredentials());
    }

    private AwsCredentials awsCredentials() {
        return AwsBasicCredentials.create(accessKeyId, secretAccessKey);
    }

    @Bean
    S3Presigner s3Presigner() {
        return S3Presigner.builder()
                .credentialsProvider(awsCredentialsProvider())
                .region(awsRegionProvider().getRegion())
                .build();
    }
}
