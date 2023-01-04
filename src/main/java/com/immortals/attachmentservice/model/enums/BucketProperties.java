package com.immortals.attachmentservice.model.enums;

public enum BucketProperties{

    SET_LIFECYCLE( false ),
    SET_POLICY( false ),
    SET_TAGS( false ),
    SET_CORS_RULE( false ),
    SET_ACL( false );

    private final Boolean value;

    BucketProperties( boolean b ){
        this.value=b;
    }
}
