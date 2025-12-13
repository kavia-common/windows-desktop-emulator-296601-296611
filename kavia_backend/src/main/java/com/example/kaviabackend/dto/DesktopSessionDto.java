package com.example.kaviabackend.dto;

import java.time.OffsetDateTime;

/**
 * Data transfer object representing a desktop session returned to clients.
 */
// PUBLIC_INTERFACE
public record DesktopSessionDto(
        Long id,
        String name,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
