package com.example.kaviabackend.dto;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Request payload for creating or updating a taskbar item.
 */
// PUBLIC_INTERFACE
public record TaskbarItemCreateRequest(
        Long windowId,
        String label,
        boolean pinned,
        int orderIndex,
        JsonNode payload
) {
}
