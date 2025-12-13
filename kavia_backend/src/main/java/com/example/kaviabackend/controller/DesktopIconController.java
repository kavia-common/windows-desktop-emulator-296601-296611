package com.example.kaviabackend.controller;

import com.example.kaviabackend.dto.DesktopIconCreateRequest;
import com.example.kaviabackend.dto.DesktopIconDto;
import com.example.kaviabackend.service.DesktopIconService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * REST controller providing operations to manage desktop icons.
 */
// PUBLIC_INTERFACE
@RestController
@RequestMapping("/api/sessions/{sessionId}/icons")
@Tag(name = "Desktop Icons", description = "Endpoints for managing desktop icons within a session.")
public class DesktopIconController {

    private final DesktopIconService iconService;

    /**
     * Creates a new {@link DesktopIconController}.
     *
     * @param iconService service used to manage icons.
     */
    // PUBLIC_INTERFACE
    public DesktopIconController(DesktopIconService iconService) {
        this.iconService = iconService;
    }

    /**
     * Lists all icons for a given session.
     *
     * @param sessionId session identifier.
     * @return list of icon DTOs.
     */
    // PUBLIC_INTERFACE
    @GetMapping
    @Operation(summary = "List icons", description = "Returns all desktop icons for a given session.")
    public ResponseEntity<List<DesktopIconDto>> listIcons(@PathVariable Long sessionId) {
        return ResponseEntity.ok(iconService.listIconsForSession(sessionId));
    }

    /**
     * Creates a new icon in the given session.
     *
     * @param sessionId session identifier.
     * @param request   payload describing the icon.
     * @return created icon DTO with a Location header.
     */
    // PUBLIC_INTERFACE
    @PostMapping
    @Operation(summary = "Create icon", description = "Creates a new desktop icon in the given session.")
    public ResponseEntity<DesktopIconDto> createIcon(
            @PathVariable Long sessionId,
            @RequestBody DesktopIconCreateRequest request
    ) {
        DesktopIconDto created = iconService.createIcon(sessionId, request);
        return ResponseEntity
                .created(URI.create("/api/sessions/" + sessionId + "/icons/" + created.id()))
                .body(created);
    }

    /**
     * Updates an existing icon in the given session.
     *
     * @param sessionId session identifier.
     * @param iconId    icon identifier.
     * @param request   payload describing the new state.
     * @return updated icon DTO.
     */
    // PUBLIC_INTERFACE
    @PutMapping("/{iconId}")
    @Operation(summary = "Update icon", description = "Updates an existing desktop icon in the given session.")
    public ResponseEntity<DesktopIconDto> updateIcon(
            @PathVariable Long sessionId,
            @PathVariable Long iconId,
            @RequestBody DesktopIconCreateRequest request
    ) {
        DesktopIconDto updated = iconService.updateIcon(sessionId, iconId, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes an icon from the given session.
     *
     * @param sessionId session identifier.
     * @param iconId    icon identifier.
     * @return empty response with HTTP 204 No Content on success.
     */
    // PUBLIC_INTERFACE
    @DeleteMapping("/{iconId}")
    @Operation(summary = "Delete icon", description = "Deletes a desktop icon from the given session.")
    public ResponseEntity<Void> deleteIcon(
            @PathVariable Long sessionId,
            @PathVariable Long iconId
    ) {
        iconService.deleteIcon(sessionId, iconId);
        return ResponseEntity.noContent().build();
    }
}
