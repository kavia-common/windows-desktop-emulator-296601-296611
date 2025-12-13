package com.example.kaviabackend.dto;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.OffsetDateTime;

/**
 * Data transfer object representing a taskbar item.
 */
// PUBLIC_INTERFACE
public record TaskbarItemDto(
        Long id,
        Long sessionId,
        Long windowId,
        String label,
        boolean pinned,
        int orderIndex,
        JsonNode payload,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
