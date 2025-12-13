package com.example.kaviabackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC configuration for the backend, including CORS settings.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Comma-separated list of allowed CORS origins.
     * <p>
     * Defaults to allowing the local React dev server and the preview frontend origin, but
     * can be overridden via standard Spring configuration mechanisms (e.g. environment
     * variables or application properties).
     */
    @Value("${kavia.cors.allowed-origins:http://localhost:3000,https://vscode-internal-27297-beta.beta01.cloud.kavia.ai:3000}")
    private String allowedOriginsProperty;

    /**
     * Configure global CORS mappings to allow the React frontend to call the backend APIs.
     *
     * @param registry the {@link CorsRegistry} to configure.
     */
    // PUBLIC_INTERFACE
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] origins = allowedOriginsProperty.split("\\s*,\\s*");

        registry.addMapping("/**")
                .allowedOrigins(origins)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
