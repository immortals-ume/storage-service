package com.immortals.attachmentservice.model.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilePayload{


    @JsonProperty ("bucketName")
    private String bucketName;

    @JsonProperty ("fileName")
    private String fileName;

    @JsonProperty ("filePath")
    private String filePath;

    @JsonProperty ("uniqueKey")
    private String key;

    @JsonProperty ("userMetaData")
    private String userMetaData;
}
