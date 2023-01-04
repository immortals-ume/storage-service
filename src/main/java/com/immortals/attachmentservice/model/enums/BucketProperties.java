package com.immortals.attachmentservice.model.enums;

public enum BucketProperties{

    SET_LIFECYCLE( "LIFECYCLE",false ),
    SET_POLICY( "POLICY",false ),
    SET_TAGS( "TAGS",false ),
    SET_CORS_RULE( "CORS_RULE",false ),
    SET_ACL( "ACL",false );

    private final String property;

    private final Boolean value;

    BucketProperties( String property,boolean b ){
        this.property=property;
        this.value=b;
    }
}
