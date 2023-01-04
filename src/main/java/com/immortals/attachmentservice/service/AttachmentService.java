package com.immortals.attachmentservice.service;


import org.springframework.web.multipart.MultipartFile;

/**
 * Attachment Service Interface defines create , update , get , getById, delete can be performed on the attachments
 */
public interface AttachmentService{


    void uploadFile( Long userId,MultipartFile file,String bucketName );

    void deleteFile();

    void updateFile();

    void downloadFile();

    void deleteS3Container();

    String createBucket( String bucketName );
}
