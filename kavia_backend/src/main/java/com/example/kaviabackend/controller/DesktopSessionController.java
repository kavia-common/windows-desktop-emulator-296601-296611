package com.example.kaviabackend.controller;

import com.example.kaviabackend.dto.DesktopSessionCreateRequest;
import com.example.kaviabackend.dto.DesktopSessionDto;
import com.example.kaviabackend.service.DesktopSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * REST controller providing operations to manage desktop sessions.
 */
// PUBLIC_INTERFACE
@RestController
@RequestMapping("/api/sessions")
@Tag(name = "Desktop Sessions", description = "Endpoints for managing desktop sessions.")
public class DesktopSessionController {

    private final DesktopSessionService sessionService;

    /**
     * Creates a new {@link DesktopSessionController}.
     *
     * @param sessionService service used to manage sessions.
     */
    // PUBLIC_INTERFACE
    public DesktopSessionController(DesktopSessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
     * Lists all desktop sessions.
     *
     * @return list of session DTOs.
     */
    // PUBLIC_INTERFACE
    @GetMapping
    @Operation(summary = "List sessions", description = "Returns all desktop sessions.")
    public ResponseEntity<List<DesktopSessionDto>> listSessions() {
        return ResponseEntity.ok(sessionService.getAllSessions());
    }

    /**
     * Retrieves a desktop session by its ID.
     *
     * @param id session identifier.
     * @return session DTO.
     */
    // PUBLIC_INTERFACE
    @GetMapping("/{id}")
    @Operation(summary = "Get session", description = "Returns a single desktop session by ID.")
    public ResponseEntity<DesktopSessionDto> getSession(@PathVariable Long id) {
        return ResponseEntity.ok(sessionService.getSession(id));
    }

    /**
     * Creates a new desktop session.
     *
     * @param request payload describing the session.
     * @return created session DTO with a Location header.
     */
    // PUBLIC_INTERFACE
    @PostMapping
    @Operation(summary = "Create session", description = "Creates a new desktop session.")
    public ResponseEntity<DesktopSessionDto> createSession(@RequestBody DesktopSessionCreateRequest request) {
        DesktopSessionDto created = sessionService.createSession(request);
        return ResponseEntity
                .created(URI.create("/api/sessions/" + created.id()))
                .body(created);
    }

    /**
     * Updates an existing desktop session's name.
     *
     * @param id      session identifier.
     * @param request payload containing the new name.
     * @return updated session DTO.
     */
    // PUBLIC_INTERFACE
    @PutMapping("/{id}")
    @Operation(summary = "Update session", description = "Updates the name of an existing desktop session.")
    public ResponseEntity<DesktopSessionDto> updateSession(
            @PathVariable Long id,
            @RequestBody DesktopSessionCreateRequest request
    ) {
        DesktopSessionDto updated = sessionService.updateSession(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes a desktop session.
     *
     * @param id session identifier.
     * @return empty response with HTTP 204 No Content on success.
     */
    // PUBLIC_INTERFACE
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete session", description = "Deletes a desktop session by ID.")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
}
