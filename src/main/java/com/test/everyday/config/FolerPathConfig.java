package com.test.everyday.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FolerPathConfig implements WebMvcConfigurer {

//	 @Value("${file.upload-dir}")
//	    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) 
    {
    	registry.addResourceHandler("/employeePics/**")
    	        .addResourceLocations("file:employeePics/");

        registry.addResourceHandler("/pfpUploads/**")
                .addResourceLocations("file:pfpUploads/");
        
//        registry.addResourceHandler("/uploadFiles/**")
//        .addResourceLocations("file:"+uploadDir+"/");


       
    }
}