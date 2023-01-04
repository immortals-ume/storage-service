package com.immortals.attachmentservice.controller;

import com.immortals.attachmentservice.model.payload.BucketPayload;
import com.immortals.attachmentservice.model.payload.FilePayload;
import com.immortals.attachmentservice.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controller Class Implements various rest controller methods create , update , get , getById, delete attachments in
 * s3 storage
 */
@RestController
@ControllerAdvice
@RequestMapping (value = "/api/v1/attachments")
public class AttachmentController{


    private final AttachmentService attachmentService;


    @Autowired
    public AttachmentController( AttachmentService service ){
        attachmentService=service;
    }

    @PostMapping (value = "{bucketName}/create")
    public String createdBucket( @PathVariable String bucketName ){
        return attachmentService.createBucket( bucketName );
    }


    @GetMapping (value = "/buckets")
    public List< BucketPayload > getListOfBuckets(){
        return attachmentService.listOfBucketsInS3( );
    }

    @PostMapping (value = "/upload", consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public String uploadFile( @RequestBody FilePayload filePayload ){
        return attachmentService.upload( filePayload );
    }
}
