package com.immortals.attachmentservice.model.payload.aws;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class S3ObjectResponsePayload{


    @JsonProperty ("fileName")
    private String objectKey;

    @JsonProperty ("lastModified")
    private Instant lastModified;

    @JsonProperty ("eTag")
    private String eTag;

    @JsonProperty ("size")
    private Long size;

    @JsonProperty ("storageType")
    private String storageClass;

    @JsonProperty ("ownerId")
    private String ownerId;
}
