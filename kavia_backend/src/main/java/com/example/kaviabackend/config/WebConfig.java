package com.example.kaviabackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC configuration for the backend, including CORS settings.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configure global CORS mappings to allow the React frontend to call the backend APIs.
     *
     * @param registry the {@link CorsRegistry} to configure.
     */
    // PUBLIC_INTERFACE
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
