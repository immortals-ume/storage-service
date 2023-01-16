package com.immortals.attachmentservice.service.aws;

import com.immortals.attachmentservice.model.payload.aws.BucketPayload;
import com.immortals.attachmentservice.model.payload.aws.FilePayload;
import com.immortals.attachmentservice.model.payload.aws.S3ObjectResponsePayload;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface s3Service {
    String createBucket(String bucketName);

    List<BucketPayload> listOfBucketsInS3();

    String upload(FilePayload filePayload);

    String uploadAsMultipartFile(MultipartFile file, FilePayload payload);

    List<S3ObjectResponsePayload> getListOfFiles(String bucketName);
}
