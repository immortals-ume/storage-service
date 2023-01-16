package com.immortals.attachmentservice.service.aws;

import com.immortals.attachmentservice.model.payload.aws.BucketPayload;
import com.immortals.attachmentservice.model.payload.aws.FilePayload;
import com.immortals.attachmentservice.model.payload.aws.S3ObjectResponsePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.*;

@Service
public class s3ServiceImpl implements s3Service {

    private final AwsStorageCoreService awsStorageCoreService;

    @Autowired
    public s3ServiceImpl(AwsStorageCoreService awsStorageCoreService) {
        this.awsStorageCoreService = awsStorageCoreService;
    }


    private static void getBuckets(List<BucketPayload> bucketPayloads, Bucket bucket) {
        BucketPayload bucketPayload = new BucketPayload();
        bucketPayload.setCreationDate(bucket.creationDate());
        bucketPayload.setName(bucket.name());
        bucketPayloads.add(bucketPayload);
    }

    /**
     * key should be handled for appending the syntax needs to be updated
     */
    private static Map<String, String> createMetaData(String data) {
        Map<String, String> metadata = new HashMap<>();
        if (Optional.ofNullable(data).isPresent()) {
            metadata.put("x-amz-meta-" + "data", data);
        }
        return metadata;
    }

    private static void getS3Objects(List<S3ObjectResponsePayload> payloads, S3Object object) {
        S3ObjectResponsePayload s3ObjectResponsePayload = new S3ObjectResponsePayload();
        s3ObjectResponsePayload.setObjectKey(object.key());
        s3ObjectResponsePayload.setLastModified(object.lastModified());
        s3ObjectResponsePayload.setSize(object.size());
        s3ObjectResponsePayload.setStorageClass(object.storageClassAsString());
        s3ObjectResponsePayload.setETag(object.eTag());
        s3ObjectResponsePayload.setOwnerId(object.owner().displayName());
        payloads.add(s3ObjectResponsePayload);
    }

    // --------------------------------------- Operations Performed On the Bucket ----------------------------------//

    @Override
    public String createBucket(String bucketName) {
        if (Boolean.TRUE.equals(awsStorageCoreService.checkIfBucketExistsOrNot(bucketName))) {
            return "Bucket Already Exists : " + bucketName;
        }
        return awsStorageCoreService.createBucket(awsStorageCoreService.createClient(), bucketName);
    }

    @Override
    public List<BucketPayload> listOfBucketsInS3() {
        List<BucketPayload> bucketPayloads = new ArrayList<>();
        for (Bucket bucket : awsStorageCoreService.listBuckets(awsStorageCoreService.createClient())) {
            getBuckets(bucketPayloads, bucket);
        }
        return bucketPayloads;
    }

    @Override
    public String upload(FilePayload filePayload) {
        return awsStorageCoreService.uploadFile(awsStorageCoreService.createClient(), filePayload.getBucketName(),
                filePayload.getKey(), createMetaData(filePayload.getFileMetaData()), filePayload.getFilePath()).eTag();
    }

    @Override
    public String uploadAsMultipartFile(MultipartFile file, FilePayload payload) {
        Boolean status = awsStorageCoreService.uploadAsMultipart(awsStorageCoreService.createClient(), payload, file);
        if (Boolean.FALSE.equals(status)) {
            return "Upload is unsuccessful";
        }
        return "Upload is SuccessFull";
    }

    @Override
    public List<S3ObjectResponsePayload> getListOfFiles(String bucketName) {
        List<S3ObjectResponsePayload> s3ObjectResponsePayloads = new ArrayList<>();
        for (S3Object s3Object : awsStorageCoreService.listObjects(awsStorageCoreService.createClient(), bucketName)) {
            getS3Objects(s3ObjectResponsePayloads, s3Object);
        }
        return s3ObjectResponsePayloads;
    }

}
