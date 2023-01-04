package com.immortals.attachmentservice.service;

import com.amazonaws.s3.model.NoSuchBucketException;
import com.immortals.attachmentservice.config.AwsConfig;
import com.immortals.attachmentservice.model.enums.BucketProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
@Component
public class AwsStorageServiceImpl implements AwsStorageService{


    private final AwsConfig awsConfig;

    private final CorsRulesOps corsRulesOps;

    @Value ("${aws.account.id}")
    private String accountId;

    @Autowired
    public AwsStorageServiceImpl( AwsConfig config,CorsRulesOps ops ){
        awsConfig=config;
        corsRulesOps=ops;
    }


    private static ByteBuffer getRandomByteBuffer( int size ){
        byte[] b=new byte[size];
        new Random( ).nextBytes( b );
        return ByteBuffer.wrap( b );
    }


    /**
     * checks if bucket already exists in the s3 storage or not
     *
     * @param bucketName
     *
     * @return
     */
    @Override
    public Boolean checkIfBucketExistsOrNot( String bucketName ){

        try {
            HeadBucketRequest headBucketRequest=HeadBucketRequest.builder( ).bucket( bucketName ).build( );
            awsConfig.createClient( ).headBucket( headBucketRequest );
            WaiterResponse< HeadBucketResponse > waiterResponse=
                    awsConfig.createClient( ).waiter( ).waitUntilBucketExists( headBucketRequest );
            waiterResponse.matched( ).response( ).ifPresent( bucketResponse->log.info( "Bucket with Name : "+bucketResponse+" already exists" ) );

            return Boolean.TRUE;
        }catch ( NoSuchBucketException e ) {
            log.error( e.getMessage( ) );
            return Boolean.FALSE;
        }
    }


    /**
     * Creates A bucket with default configuration
     *
     * @param s3Client
     * @param bucketName
     *
     * @return
     */
    @Override
    public String createBucket( S3Client s3Client,String bucketName,Map< String,Boolean > bucketProperties,
                                List< String > allowOrigins,
                                List< String > allowMethods ){
        try {

            S3Waiter s3Waiter=s3Client.waiter( );
            CreateBucketRequest bucketRequest=CreateBucketRequest.builder( ).bucket( bucketName ).build( );

            s3Client.createBucket( bucketRequest );
            HeadBucketRequest bucketRequestWait=HeadBucketRequest.builder( ).bucket( bucketName ).build( );

            // Wait until the bucket is created and print out the response.
            WaiterResponse< HeadBucketResponse > waiterResponse=s3Waiter.waitUntilBucketExists( bucketRequestWait );
            waiterResponse.matched( ).response( ).ifPresent( bucketResponse->log.info( "Bucket with Name : "+bucketResponse+" is ready" ) );
            log.info( bucketName+" is ready" );

            if ( bucketProperties.get( BucketProperties.SET_CORS_RULE.name( ) ) ) {
                corsRulesOps.setCorsInformation( s3Client,bucketName,accountId,allowOrigins,allowMethods );
            }
            if ( bucketProperties.get( BucketProperties.SET_LIFECYCLE.name( ) ) ) {

            }

        }catch ( S3Exception e ) {
            log.error( e.awsErrorDetails( ).errorMessage( ) );
        }
        return bucketName;
    }

    /**
     * List the buckets for the specified account
     *
     * @param s3Client s3 storage client object
     *
     * @return list of bucket for  the client
     */
    @Override
    public List< Bucket > listBuckets( S3Client s3Client ){
        return s3Client.listBuckets( ).buckets( );
    }


    // --------------------------------------- Operations Performed On the Bucket ----------------------------------//
    @Override
    public void uploadMultipart( MultipartFile file,String bucketName,String key ){
        try {
            CreateMultipartUploadRequest createMultipartUploadRequest=CreateMultipartUploadRequest.builder( )
                    .bucket( bucketName )
                    .key( key )
                    .build( );

            CreateMultipartUploadResponse response=
                    awsConfig.createClient( ).createMultipartUpload( createMultipartUploadRequest );

            log.info( response.uploadId( ) );


            UploadPartRequest uploadPartRequest1=UploadPartRequest.builder( )
                    .bucket( bucketName )
                    .key( key )
                    .uploadId( response.uploadId( ) )
                    .partNumber( 1 ).build( );

            int maxSize=1024*1024;
            String etag1=awsConfig.createClient( ).uploadPart( uploadPartRequest1,
                    RequestBody.fromByteBuffer( getRandomByteBuffer( 5*maxSize ) ) ).eTag( );

            CompletedPart part1=CompletedPart.builder( ).partNumber( 1 ).eTag( etag1 ).build( );

            UploadPartRequest uploadPartRequest2=UploadPartRequest.builder( ).bucket( bucketName ).key( key )
                    .uploadId( response.uploadId( ) )
                    .partNumber( 2 ).build( );
            String etag2=awsConfig.createClient( ).uploadPart( uploadPartRequest2,
                    RequestBody.fromByteBuffer( getRandomByteBuffer( 3*maxSize ) ) ).eTag( );
            CompletedPart part2=CompletedPart.builder( ).partNumber( 2 ).eTag( etag2 ).build( );


            CompletedMultipartUpload completedMultipartUpload=CompletedMultipartUpload.builder( )
                    .parts( part1,part2 )
                    .build( );

            CompleteMultipartUploadRequest completeMultipartUploadRequest=
                    CompleteMultipartUploadRequest.builder( )
                            .bucket( bucketName )
                            .key( key )
                            .uploadId( response.uploadId( ) )
                            .multipartUpload( completedMultipartUpload )
                            .build( );

            awsConfig.createClient( ).completeMultipartUpload( completeMultipartUploadRequest );
        }catch ( S3Exception exception ) {
            log.error( exception.getMessage( ) );
        }
    }
}
