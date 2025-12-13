package com.example.kaviabackend.dto;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.OffsetDateTime;

/**
 * Data transfer object representing a desktop window.
 */
// PUBLIC_INTERFACE
public record DesktopWindowDto(
        Long id,
        Long sessionId,
        String title,
        int x,
        int y,
        int width,
        int height,
        int zIndex,
        String state,
        JsonNode payload,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
