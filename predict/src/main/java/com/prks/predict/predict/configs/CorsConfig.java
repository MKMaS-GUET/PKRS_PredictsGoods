package com.prks.predict.predict.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    static final String ORIGINS[] = new String[]{"GET", "POST", "PUT", "DELETE"};

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        /*
         * 一小时内不需要再预检
         */
        registry.addMapping("/**").
                allowedOriginPatterns("*").
                allowCredentials(true).
                allowedMethods(ORIGINS).
                maxAge(3600);

    }
}

