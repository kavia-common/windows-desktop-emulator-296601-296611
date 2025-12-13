package com.example.kaviabackend.dto;

/**
 * Request payload for creating or updating a desktop session.
 */
// PUBLIC_INTERFACE
public record DesktopSessionCreateRequest(
        String name
) {
}
