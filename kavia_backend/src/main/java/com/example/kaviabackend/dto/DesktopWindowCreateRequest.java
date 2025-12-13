package com.example.kaviabackend.dto;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Request payload for creating or updating a desktop window.
 */
// PUBLIC_INTERFACE
public record DesktopWindowCreateRequest(
        String title,
        int x,
        int y,
        int width,
        int height,
        int zIndex,
        String state,
        JsonNode payload
) {
}
