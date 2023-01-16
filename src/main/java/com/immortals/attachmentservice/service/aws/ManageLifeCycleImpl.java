package com.immortals.attachmentservice.service.aws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ManageLifeCycleImpl implements ManageLifeCycle{


    private static BucketLifecycleConfiguration getBuild( List< LifecycleRule > lifecycleRules ){
        return BucketLifecycleConfiguration.builder( )
                .rules( lifecycleRules )
                .build( );
    }

    @Override
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

    @Override
    public List< LifecycleRule > getBucketLifeCycle( S3Client s3Client,String bucketName,String accountId ){
        List< LifecycleRule > lifecycleRules=new ArrayList<>( );
        try {
            GetBucketLifecycleConfigurationRequest getBucketLifecycleConfigurationRequest=
                    GetBucketLifecycleConfigurationRequest.builder( )
                            .bucket( bucketName )
                            .expectedBucketOwner( accountId )
                            .build( );

            lifecycleRules=s3Client.getBucketLifecycleConfiguration( getBucketLifecycleConfigurationRequest ).rules( );

        }catch ( S3Exception e ) {
            log.error( e.awsErrorDetails( ).errorMessage( ) );
        }
        return lifecycleRules;
    }

    @Override
    public DeleteBucketLifecycleResponse deleteBucketLifeCycle( S3Client s3Client,String bucketName,String accountId ){
        DeleteBucketLifecycleResponse deleteBucketLifecycleResponse=null;
        try {
            DeleteBucketLifecycleRequest deleteBucketLifecycleRequest=DeleteBucketLifecycleRequest.builder( )
                    .bucket( bucketName )
                    .expectedBucketOwner( accountId )
                    .build( );

            deleteBucketLifecycleResponse=s3Client.deleteBucketLifecycle( deleteBucketLifecycleRequest );

        }catch ( S3Exception e ) {
            log.error( e.awsErrorDetails( ).errorMessage( ) );
        }
        return deleteBucketLifecycleResponse;
    }
}
