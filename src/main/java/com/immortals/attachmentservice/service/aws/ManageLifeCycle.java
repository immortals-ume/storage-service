package com.immortals.attachmentservice.service.aws;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteBucketLifecycleResponse;
import software.amazon.awssdk.services.s3.model.LifecycleRule;
import software.amazon.awssdk.services.s3.model.PutBucketLifecycleConfigurationResponse;

import java.util.List;

public interface ManageLifeCycle{


    PutBucketLifecycleConfigurationResponse setBucketLifeCycle( S3Client s3Client,String bucketName,
                                                                String accountId,
                                                                List< LifecycleRule > lifecycleRules );

    List< LifecycleRule > getBucketLifeCycle( S3Client s3Client,String bucketName,String accountId );

    DeleteBucketLifecycleResponse deleteBucketLifeCycle( S3Client s3Client,String bucketName,String accountId );
}
