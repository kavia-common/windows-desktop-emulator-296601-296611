package com.example.kaviabackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a requested resource cannot be found.
 */
// PUBLIC_INTERFACE
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@link ResourceNotFoundException} with the given message.
     *
     * @param message description of the missing resource.
     */
    // PUBLIC_INTERFACE
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
