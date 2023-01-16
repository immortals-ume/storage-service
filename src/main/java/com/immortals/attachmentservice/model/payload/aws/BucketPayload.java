package com.immortals.attachmentservice.model.payload.aws;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class BucketPayload{

  private String name;
  private Instant creationDate;
}
