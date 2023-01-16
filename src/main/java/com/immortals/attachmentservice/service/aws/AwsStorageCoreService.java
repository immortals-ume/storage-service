package com.immortals.attachmentservice.service.aws;

import com.immortals.attachmentservice.model.payload.aws.FilePayload;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.LifecycleRule;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.List;
import java.util.Map;

public interface AwsStorageCoreService {


    S3Client createClient();

    boolean checkIfBucketExistsOrNot(String bucketName);

    String createBucket(S3Client s3Client, String bucketName);

    void setBucketProperties(Map<String, Boolean> bucketProperties,
                             List<String> allowOrigins,
                              List< String > allowMethods,List< LifecycleRule > lifecycleRules );

    List< Bucket > listBuckets( S3Client s3Client );

    // --------------------------------------- Operations Performed On the Bucket ----------------------------------//

    PutObjectResponse uploadFile( S3Client s3Client,String bucketName,String key,
                                  Map< String,String > metadata,String filePath );

    Boolean uploadAsMultipart( S3Client s3Client,FilePayload payload,MultipartFile multipartFile );

    List< S3Object > listObjects( S3Client client,String bucketName );
}
