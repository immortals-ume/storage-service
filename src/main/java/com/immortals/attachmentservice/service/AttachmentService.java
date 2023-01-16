package com.immortals.attachmentservice.service;


import com.immortals.attachmentservice.model.payload.BucketPayload;
import com.immortals.attachmentservice.model.payload.FilePayload;
import com.immortals.attachmentservice.model.payload.S3ObjectResponsePayload;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Attachment Service Interface defines create , update , get , getById, delete can be performed on the attachments
 */
public interface AttachmentService{


    String createBucket( String bucketName );

    List< BucketPayload > listOfBucketsInS3();

    String upload( FilePayload filePayload );

    String uploadAsMultipartFile( MultipartFile file,FilePayload payload );

    List< S3ObjectResponsePayload > getListOfFiles( String bucketName );

}
