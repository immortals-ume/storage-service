package com.immortals.attachmentservice.controller;

import com.immortals.attachmentservice.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * Controller Class Implements various rest controller methods create , update , get , getById, delete attachments in
 * s3 storage
 */
@RestController
@ControllerAdvice
@RequestMapping (name = "/api/v1/attachments")
public class AttachmentController{


    private final AttachmentService attachmentService;

    @Autowired
    public AttachmentController( AttachmentService service ){attachmentService=service;}

    @PostMapping (value = "{bucketName}/create", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces =
            { MediaType.APPLICATION_JSON_VALUE })
    public String createdBucket( String bucketName ){
        return attachmentService.createBucket( bucketName );
    }

    /**
     * Uploads the File , provided in the request in multipart format
     *
     * @return
     */
    @PostMapping (value = "{userId}/upload/{bucketName}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public void uploadFile( @PathVariable Long userId,@RequestPart MultipartFile file,@PathVariable String bucketName ){
        attachmentService.uploadFile( userId,file,bucketName );
    }
}
