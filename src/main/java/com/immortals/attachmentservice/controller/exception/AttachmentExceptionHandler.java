package com.immortals.attachmentservice.controller.exception;

import com.immortals.attachmentservice.controller.aws.S3Controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(basePackageClasses = {S3Controller.class})
@Slf4j
public class AttachmentExceptionHandler extends ResponseEntityExceptionHandler{


    @Override
    protected ResponseEntity< Object > handleMethodArgumentNotValid( MethodArgumentNotValidException ex,
                                                                     HttpHeaders headers,HttpStatusCode status,
                                                                     WebRequest request ){
        return super.handleMethodArgumentNotValid( ex,headers,status,request );
    }

    @Override
    protected ResponseEntity< Object > handleMissingServletRequestPart( MissingServletRequestPartException ex,
                                                                        HttpHeaders headers,HttpStatusCode status,
                                                                        WebRequest request ){
        return super.handleMissingServletRequestPart( ex,headers,status,request );
    }

    @Override
    protected ResponseEntity< Object > handleMissingPathVariable( MissingPathVariableException ex,HttpHeaders headers,
                                                                  HttpStatusCode status,WebRequest request ){
        return super.handleMissingPathVariable( ex,headers,status,request );
    }
}
