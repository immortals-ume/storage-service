package com.immortals.attachmentservice.model.error;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class ApiErrorResponse{


    private HttpStatus httpStatus;

    private Instant timeStamp;

    private String moreInfo;

    private Integer errorCode;

    private String errorMessage;

    private List< String > errors;

    public ApiErrorResponse( HttpStatus httpStatus,Instant timeStamp,String moreInfo,Integer errorCode,
                             String errorMessage,List< String > errors ){
        this.httpStatus=httpStatus;
        this.timeStamp=timeStamp;
        this.moreInfo=moreInfo;
        this.errorCode=errorCode;
        this.errorMessage=errorMessage;
        this.errors=errors;
    }
}
