package com.example.kaviabackend.dto;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Request payload for creating or updating a desktop icon.
 */
// PUBLIC_INTERFACE
public record DesktopIconCreateRequest(
        String title,
        int x,
        int y,
        JsonNode payload
) {
}
