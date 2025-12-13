package com.example.kaviabackend.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI configuration for the Kavia backend.
 * <p>
 * This configuration customizes the OpenAPI metadata used by springdoc so that
 * the generated Swagger UI (served via {@code /docs}) is well described.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Builds the base OpenAPI metadata for the backend.
     *
     * @return an {@link OpenAPI} instance describing the Kavia API.
     */
    // PUBLIC_INTERFACE
    @Bean
    public OpenAPI kaviaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Kavia Windows Desktop Emulator API")
                        .description("REST API for managing desktop sessions, icons, windows, and taskbar state.")
                        .version("0.1.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("Swagger UI is available via the /docs endpoint."));
    }
}
