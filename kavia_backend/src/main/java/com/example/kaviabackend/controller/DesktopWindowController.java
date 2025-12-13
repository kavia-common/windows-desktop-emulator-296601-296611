package com.example.kaviabackend.controller;

import com.example.kaviabackend.dto.DesktopWindowCreateRequest;
import com.example.kaviabackend.dto.DesktopWindowDto;
import com.example.kaviabackend.service.DesktopWindowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * REST controller providing operations to manage desktop windows.
 */
// PUBLIC_INTERFACE
@RestController
@RequestMapping("/api/sessions/{sessionId}/windows")
@Tag(name = "Desktop Windows", description = "Endpoints for managing desktop windows within a session.")
public class DesktopWindowController {

    private final DesktopWindowService windowService;

    /**
     * Creates a new {@link DesktopWindowController}.
     *
     * @param windowService service used to manage windows.
     */
    // PUBLIC_INTERFACE
    public DesktopWindowController(DesktopWindowService windowService) {
        this.windowService = windowService;
    }

    /**
     * Lists all windows for a given session.
     *
     * @param sessionId session identifier.
     * @return list of window DTOs.
     */
    // PUBLIC_INTERFACE
    @GetMapping
    @Operation(summary = "List windows", description = "Returns all windows for a given session.")
    public ResponseEntity<List<DesktopWindowDto>> listWindows(@PathVariable Long sessionId) {
        return ResponseEntity.ok(windowService.listWindowsForSession(sessionId));
    }

    /**
     * Creates a new window in the given session.
     *
     * @param sessionId session identifier.
     * @param request   payload describing the window.
     * @return created window DTO with a Location header.
     */
    // PUBLIC_INTERFACE
    @PostMapping
    @Operation(summary = "Create window", description = "Creates a new window in the given session.")
    public ResponseEntity<DesktopWindowDto> createWindow(
            @PathVariable Long sessionId,
            @RequestBody DesktopWindowCreateRequest request
    ) {
        DesktopWindowDto created = windowService.createWindow(sessionId, request);
        return ResponseEntity
                .created(URI.create("/api/sessions/" + sessionId + "/windows/" + created.id()))
                .body(created);
    }

    /**
     * Updates an existing window in the given session.
     *
     * @param sessionId session identifier.
     * @param windowId  window identifier.
     * @param request   payload describing new window state.
     * @return updated window DTO.
     */
    // PUBLIC_INTERFACE
    @PutMapping("/{windowId}")
    @Operation(summary = "Update window", description = "Updates an existing window in the given session.")
    public ResponseEntity<DesktopWindowDto> updateWindow(
            @PathVariable Long sessionId,
            @PathVariable Long windowId,
            @RequestBody DesktopWindowCreateRequest request
    ) {
        DesktopWindowDto updated = windowService.updateWindow(sessionId, windowId, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes a window from the given session.
     *
     * @param sessionId session identifier.
     * @param windowId  window identifier.
     * @return empty response with HTTP 204 No Content on success.
     */
    // PUBLIC_INTERFACE
    @DeleteMapping("/{windowId}")
    @Operation(summary = "Delete window", description = "Deletes a window from the given session.")
    public ResponseEntity<Void> deleteWindow(
            @PathVariable Long sessionId,
            @PathVariable Long windowId
    ) {
        windowService.deleteWindow(sessionId, windowId);
        return ResponseEntity.noContent().build();
    }
}
