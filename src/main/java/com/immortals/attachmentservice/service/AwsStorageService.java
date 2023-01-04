package com.immortals.attachmentservice.service;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;

import java.util.List;

public interface AwsStorageService{


    Boolean checkIfBucketExistsOrNot( String bucketName );

    String createBucket( S3Client s3Client,String bucketName,Boolean isSetCorsBucket,
                         List< String > allowOrigins,
                         List< String > allowMethods );

    List< Bucket > listBuckets( S3Client s3Client );


    void uploadMultipart( MultipartFile file,String bucketName,String key );


}
