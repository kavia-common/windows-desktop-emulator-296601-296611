package com.example.kaviabackend.dto;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.OffsetDateTime;

/**
 * Data transfer object representing a desktop icon.
 */
// PUBLIC_INTERFACE
public record DesktopIconDto(
        Long id,
        Long sessionId,
        String title,
        int x,
        int y,
        JsonNode payload,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
