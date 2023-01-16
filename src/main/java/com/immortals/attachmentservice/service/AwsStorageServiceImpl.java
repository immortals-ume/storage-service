package com.immortals.attachmentservice.service;

import com.immortals.attachmentservice.model.payload.FilePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
@Component
public class AwsStorageServiceImpl implements AwsStorageService{


    private final CorsRulesOps corsRulesOps;

    private final ManageLifeCycle manageLifeCycle;

    @Value ("${aws.account.id}")
    private String accountId;

    @Value ("${aws.access.key}")
    private String accessKey;

    @Value ("${aws.secret.key}")
    private String secretKey;

    @Autowired
    public AwsStorageServiceImpl( CorsRulesOps ops,ManageLifeCycle cycle ){
        corsRulesOps=ops;
        manageLifeCycle=cycle;
    }

    private static ByteBuffer getRandomByteBuffer( int size ){
        byte[] b=new byte[size];
        new Random( ).nextBytes( b );
        return ByteBuffer.wrap( b );
    }

    private AwsBasicCredentials credentials(){
        return AwsBasicCredentials.create( accessKey,secretKey );

    }

    @Override
    public S3Client createClient(){
        Region region=Region.AP_SOUTH_1;
        return S3Client.builder( ).region( region ).credentialsProvider( StaticCredentialsProvider.create( credentials( ) ) ).build( );

    }
    // --------------------------------------- Operations Performed with the Bucket ----------------------------------//

    /**
     * checks if bucket already exists in the s3 storage or not
     *
     * @param bucketName name of the bucket
     *
     * @return true if the bucket is found present already in the s3 storage
     *         false , if any error  occurs and bucket is not found
     */
    @Override
    public boolean checkIfBucketExistsOrNot( String bucketName ){
        S3Client s3Client=createClient( );
        boolean isExists;
        try {
            HeadBucketRequest headBucketRequest=HeadBucketRequest.builder( ).bucket( bucketName ).build( );
            s3Client.headBucket( headBucketRequest );
            WaiterResponse< HeadBucketResponse > waiterResponse=
                    s3Client.waiter( ).waitUntilBucketExists( headBucketRequest );
            waiterResponse.matched( ).response( );
            isExists=Boolean.TRUE;
        }catch ( NoSuchBucketException e ) {
            isExists=Boolean.FALSE;
            log.error( e.awsErrorDetails( ).errorMessage( ) );
        }finally {
            s3Client.close( );
        }
        return isExists;
    }


    /**
     * Creates A bucket with default configuration
     *
     * @param s3Client
     * @param bucketName
     *
     * @return status of bucket creation
     */
    @Override
    public String createBucket( S3Client s3Client,String bucketName ){

        String statusOfBucketCreation;
        try {

            S3Waiter s3Waiter=s3Client.waiter( );
            CreateBucketRequest bucketRequest=CreateBucketRequest.builder( ).bucket( bucketName ).build( );

            s3Client.createBucket( bucketRequest );
            HeadBucketRequest bucketRequestWait=HeadBucketRequest.builder( ).bucket( bucketName ).build( );

            // Wait until the bucket is created and print out the response.
            WaiterResponse< HeadBucketResponse > waiterResponse=s3Waiter.waitUntilBucketExists( bucketRequestWait );
            waiterResponse.matched( ).response( ).ifPresent( bucketResponse->log.info( "Bucket with Name : "+bucketResponse+" is ready" ) );
            log.info( bucketName+" is ready" );
            statusOfBucketCreation="New Bucket with : "+bucketName+"created";

        }catch ( S3Exception e ) {
            statusOfBucketCreation="Error creating Bucket with name : "+bucketName;
            log.error( e.awsErrorDetails( ).errorMessage( ) );
        }
        return statusOfBucketCreation;
    }

    @Override
    public void setBucketProperties( Map< String,Boolean > bucketProperties,List< String > allowOrigins,
                                     List< String > allowMethods,List< LifecycleRule > lifecycleRules ){
        //TODO : customizable implementation of set props for the bucket
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
    public PutObjectResponse uploadFile( S3Client s3Client,String bucketName,String key,
                                         Map< String,String > metadata,String filePath ){
        checkIfBucketExistsOrNot( bucketName );
        PutObjectResponse putObjectResponse=null;
        try ( FileInputStream fileInputStream=new FileInputStream( new File( filePath ) ) ) {
            PutObjectRequest putOb=PutObjectRequest.builder( )
                    .bucket( bucketName )
                    .key( key )
                    .metadata( metadata )
                    .build( );

            putObjectResponse=s3Client.putObject( putOb,
                    RequestBody.fromInputStream( fileInputStream,
                            new File( filePath ).length( ) ) );

        }catch ( S3Exception|IOException exception ) {
            log.error( exception.getMessage( ) );
        }
        return putObjectResponse;
    }

    @Override
    public Boolean uploadAsMultipart( S3Client s3Client,FilePayload payload,MultipartFile multipartFile ){
        boolean status=false;
        try {
            int maxUploadThreads=5;
            status=true;
            log.info( "Upload complete." );
        }catch ( S3Exception e ) {
            status=false;
            log.error( e.awsErrorDetails( ).errorMessage( ) );
        }
        return status;
    }

    @Override
    public List< S3Object > listObjects( S3Client s3Client ,String bucketName ){
        List<S3Object>  objects = new ArrayList<>();
       try {
           ListObjectsRequest listObjectsRequest = ListObjectsRequest
                   .builder()
                   .bucket(bucketName)
                   .build();
           objects = s3Client.listObjects(listObjectsRequest).contents();

       }catch(S3Exception e){
           log.error( e.awsErrorDetails().errorMessage() );
       }
       return objects;
    }


}
