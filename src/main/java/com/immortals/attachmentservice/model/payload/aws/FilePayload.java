package com.immortals.attachmentservice.model.payload.aws;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilePayload{

    @NotNull
    @JsonProperty ("bucketName")
    private String bucketName;

    @NotNull
    @JsonProperty ("fileName")
    private String fileName;

    @JsonProperty ("filePath")
    private String filePath;

    @NotNull
    @JsonProperty ("uniqueKey")
    private String key;

    @JsonProperty ("fileMetaData")
    private String fileMetaData;
}
