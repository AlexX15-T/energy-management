package com.ds.spring.p1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") // Or the specific path pattern
//                .allowedOriginPatterns("http://localhost:[*]") // Use a pattern to match your origins
//                .allowCredentials(true)
//                .allowedMethods("GET", "POST", "PUT", "DELETE") // etc.
//                .allowedHeaders("*"); // Or specify actual headers
//    }

}
