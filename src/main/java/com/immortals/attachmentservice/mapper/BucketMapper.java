package com.immortals.attachmentservice.mapper;

import com.immortals.attachmentservice.model.payload.BucketPayload;
import org.mapstruct.Mapper;
import software.amazon.awssdk.services.s3.model.Bucket;

@Mapper (componentModel = "spring")
public interface BucketMapper{


    BucketPayload getBuckets( Bucket buckets );
}
