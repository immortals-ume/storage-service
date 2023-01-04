package com.immortals.attachmentservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsConfig{


    @Value ("${aws.access.key}")
    private String accessKey;

    @Value ("${aws.secret.key}")
    private String secretKey;

    private AwsBasicCredentials credentials(){
        return AwsBasicCredentials.create( accessKey,secretKey );

    }

    @Bean
    public S3Client createClient(){
        Region region=Region.AP_SOUTH_1;
        return S3Client.builder( ).region( region ).credentialsProvider( StaticCredentialsProvider.create( credentials( ) ) ).build( );

    }

}
