package com.immortals.attachmentservice.service.aws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the CORS Rules , create , update delete  of CORS rule for specific bucket in any specific account
 */
@Slf4j
@Component
public class CorsRulesOpsImpl implements CorsRulesOps{


    private static CORSConfiguration getConfiguration( List< String > allowOrigins,List< String > allowMethods ){
        CORSRule corsRule=CORSRule.builder( )
                .allowedMethods( allowMethods )
                .allowedOrigins( allowOrigins )
                .build( );

        List< CORSRule > corsRules=new ArrayList<>( );
        corsRules.add( corsRule );
        return CORSConfiguration.builder( )
                .corsRules( corsRules )
                .build( );
    }

    @Override
    public PutBucketCorsResponse setCorsInformation( S3Client s3Client,String bucketName,String accountId,
                                                     List< String > allowOrigins,
                                                     List< String > allowMethods ){

        PutBucketCorsResponse putBucketCorsResponse=null;
        try {
            CORSConfiguration configuration=getConfiguration( allowOrigins,allowMethods );

            PutBucketCorsRequest putBucketCorsRequest=PutBucketCorsRequest.builder( )
                    .bucket( bucketName )
                    .corsConfiguration( configuration )
                    .expectedBucketOwner( accountId )
                    .build( );

            putBucketCorsResponse=s3Client.putBucketCors( putBucketCorsRequest );
        }catch ( S3Exception e ) {
            log.error( e.awsErrorDetails( ).errorMessage( ) );
        }
        return putBucketCorsResponse;
    }

    @Override
    public List< CORSRule > getBucketCorsInformation( S3Client s3Client,String bucketName,String accountId ){
        List< CORSRule > corsRules=new ArrayList<>( );
        try {
            GetBucketCorsRequest bucketCorsRequest=GetBucketCorsRequest.builder( )
                    .bucket( bucketName )
                    .expectedBucketOwner( accountId )
                    .build( );

            GetBucketCorsResponse corsResponse=s3Client.getBucketCors( bucketCorsRequest );
            corsRules=corsResponse.corsRules( );

        }catch ( S3Exception e ) {
            log.error( e.awsErrorDetails( ).errorMessage( ) );
        }
        return corsRules;
    }

    @Override
    public DeleteBucketCorsResponse deleteBucketCorsInformation( S3Client s3Client,String bucketName,String accountId ){
        DeleteBucketCorsResponse deleteBucketCorsResponse=null;
        try {
            DeleteBucketCorsRequest bucketCorsRequest=DeleteBucketCorsRequest.builder( )
                    .bucket( bucketName )
                    .expectedBucketOwner( accountId )
                    .build( );

            deleteBucketCorsResponse=s3Client.deleteBucketCors( bucketCorsRequest );
        }catch ( S3Exception e ) {
            log.error( e.awsErrorDetails( ).errorMessage( ) );
        }
        return deleteBucketCorsResponse;
    }
}
