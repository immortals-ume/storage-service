package com.immortals.attachmentservice.service;


import com.immortals.attachmentservice.config.AwsConfig;
import com.immortals.attachmentservice.repository.AttachmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class AttachmentServiceImpl implements AttachmentService{


    private final AttachmentRepository attachmentRepository;

    private final AwsStorageService awsStorageService;

    private final AwsConfig awsConfig;

    @Autowired
    public AttachmentServiceImpl( AttachmentRepository repository,AwsStorageService service,AwsConfig config ){
        attachmentRepository=repository;
        awsStorageService=service;
        awsConfig=config;
    }

    @Override
    public String createBucket( String bucketName ){
        if ( awsStorageService.checkIfBucketExistsOrNot( bucketName ) ) {
            log.info( bucketName+"exists" );
        }
        return awsStorageService.createBucket( awsConfig.createClient( ),bucketName );
    }

    @Override
    public void uploadFile( Long userId,MultipartFile file,String bucketName ){
        String bucketCreated=createBucket( bucketName );
        if ( bucketCreated!=null ) {
            awsStorageService.uploadMultipart( file,bucketName,"multipartUpload" );
        }
    }

    public void deleteFile(){

    }

    public void updateFile(){

    }

    public void downloadFile(){

    }

    public void deleteS3Container(){

    }


}
