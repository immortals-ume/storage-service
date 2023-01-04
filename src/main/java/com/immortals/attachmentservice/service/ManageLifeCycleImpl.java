package com.immortals.attachmentservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.List;

@Component
@Slf4j
public class ManageLifeCycleImpl{


    private static BucketLifecycleConfiguration getBuild( List< LifecycleRule > lifecycleRules ){
        return BucketLifecycleConfiguration.builder( )
                .rules( lifecycleRules )
                .build( );
    }

    public PutBucketLifecycleConfigurationResponse setBucketLifeCycle( S3Client s3Client,String bucketName,
                                                                       String accountId,
                                                                       List< LifecycleRule > lifecycleRules ){
        PutBucketLifecycleConfigurationResponse putBucketLifecycleConfigurationResponse=null;
        try {
            BucketLifecycleConfiguration lifecycleConfiguration=getBuild( lifecycleRules );

            PutBucketLifecycleConfigurationRequest putBucketLifecycleConfigurationRequest=
                    PutBucketLifecycleConfigurationRequest.builder( )
                            .bucket( bucketName )
                            .lifecycleConfiguration( lifecycleConfiguration )
                            .expectedBucketOwner( accountId )
                            .build( );

            putBucketLifecycleConfigurationResponse=
                    s3Client.putBucketLifecycleConfiguration( putBucketLifecycleConfigurationRequest );

        }catch ( S3Exception e ) {
            log.error( e.awsErrorDetails( ).errorMessage( ) );
        }
        return putBucketLifecycleConfigurationResponse;
    }

    public void getBucketLifeCycle( S3Client s3Client,String bucketName,String accountId ){

    }
}
