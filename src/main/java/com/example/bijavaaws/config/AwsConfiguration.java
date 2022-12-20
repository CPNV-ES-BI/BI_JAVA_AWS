package com.example.bijavaaws.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.awspring.cloud.core.region.StaticRegionProvider;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.providers.AwsRegionProvider;

@Configuration
class AwsConfiguration {

    @Value("${aws.region}")
    private String region;

    @Value("${aws.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.secretAccessKey}")
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
}
