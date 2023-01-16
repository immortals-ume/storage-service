package com.immortals.attachmentservice.service.aws;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CORSRule;
import software.amazon.awssdk.services.s3.model.DeleteBucketCorsResponse;
import software.amazon.awssdk.services.s3.model.PutBucketCorsResponse;

import java.util.List;

public interface CorsRulesOps{


    PutBucketCorsResponse setCorsInformation( S3Client s3Client,String bucketName,String accountId,
                                              List< String > allowOrigins,
                                              List< String > allowMethods );


    List< CORSRule > getBucketCorsInformation( S3Client s3Client,String bucketName,String accountId );

    DeleteBucketCorsResponse deleteBucketCorsInformation( S3Client s3Client,String bucketName,String accountId );
}
