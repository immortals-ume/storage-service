package com.immortals.attachmentservice.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableCaching
public class WebMvcConfig implements WebMvcConfigurer{


    @Override
    public void addCorsMappings( CorsRegistry registry ){
        long maxAgeSeconds=3600;
        registry.addMapping( "/**" )
                .allowedOrigins( "*" )
                .allowedMethods( "HEAD","OPTIONS","GET","POST","PUT","PATCH","DELETE" )
                .maxAge( maxAgeSeconds );
    }
}
