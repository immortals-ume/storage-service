package com.immortals.attachmentservice.service;


import com.immortals.attachmentservice.mapper.BucketMapper;
import com.immortals.attachmentservice.model.payload.BucketPayload;
import com.immortals.attachmentservice.model.payload.FilePayload;
import com.immortals.attachmentservice.repository.AttachmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.Bucket;

import java.util.*;

@Slf4j
@Service
public class AttachmentServiceImpl implements AttachmentService{


    private final AttachmentRepository attachmentRepository;

    private final AwsStorageService awsStorageService;

    private final BucketMapper bucketMapper;

    @Autowired
    public AttachmentServiceImpl( AttachmentRepository repository,AwsStorageService service,BucketMapper mapper ){
        attachmentRepository=repository;
        awsStorageService=service;
        bucketMapper=mapper;
    }

    @Override
    public String createBucket( String bucketName ){
        if ( Boolean.TRUE.equals( awsStorageService.checkIfBucketExistsOrNot( bucketName ) ) ) {
            return "Bucket Already Exists : "+bucketName;
        }
        return awsStorageService.createBucket( awsStorageService.createClient( ),bucketName );
    }

    @Override
    public List< BucketPayload > listOfBucketsInS3(){
        List< BucketPayload > bucketPayloads=new ArrayList<>( );
        for ( Bucket bucket: awsStorageService.listBuckets( awsStorageService.createClient( ) ) ) {
            BucketPayload bucketPayload=new BucketPayload( );
            bucketPayload.setCreationDate( bucket.creationDate( ) );
            bucketPayload.setName( bucket.name( ) );
            bucketPayloads.add( bucketPayload );
        }
        return bucketPayloads;
    }

    // --------------------------------------- Operations Performed On the Bucket ----------------------------------//

    @Override
    public String upload( FilePayload filePayload ){
        return awsStorageService.uploadFile( awsStorageService.createClient( ),filePayload.getBucketName( ),
                filePayload.getKey( ),createMetaData( filePayload.getUserMetaData( ) ),filePayload.getFilePath( ) ).eTag( );
    }

    /**
     * key should be handled for appending the syntax needs to be updated
     */
    private Map< String,String > createMetaData( String data ){
        Map< String,String > metadata=new HashMap<>( );
        if ( Optional.ofNullable( data ).isPresent( ) ) {
            metadata.put( "x-amz-meta-"+"data",data );
        }
        return metadata;
    }

}
