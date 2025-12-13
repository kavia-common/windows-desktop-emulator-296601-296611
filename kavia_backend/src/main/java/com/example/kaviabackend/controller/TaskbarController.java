package com.example.kaviabackend.controller;

import com.example.kaviabackend.dto.TaskbarItemCreateRequest;
import com.example.kaviabackend.dto.TaskbarItemDto;
import com.example.kaviabackend.service.TaskbarItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * REST controller providing operations to manage taskbar items.
 */
// PUBLIC_INTERFACE
@RestController
@RequestMapping("/api/sessions/{sessionId}/taskbar")
@Tag(name = "Taskbar", description = "Endpoints for managing taskbar items within a session.")
public class TaskbarController {

    private final TaskbarItemService taskbarItemService;

    /**
     * Creates a new {@link TaskbarController}.
     *
     * @param taskbarItemService service used to manage taskbar items.
     */
    // PUBLIC_INTERFACE
    public TaskbarController(TaskbarItemService taskbarItemService) {
        this.taskbarItemService = taskbarItemService;
    }

    /**
     * Lists all taskbar items for a given session.
     *
     * @param sessionId session identifier.
     * @return list of taskbar item DTOs.
     */
    // PUBLIC_INTERFACE
    @GetMapping
    @Operation(summary = "List taskbar items", description = "Returns all taskbar items for a given session.")
    public ResponseEntity<List<TaskbarItemDto>> listItems(@PathVariable Long sessionId) {
        return ResponseEntity.ok(taskbarItemService.listItemsForSession(sessionId));
    }

    /**
     * Creates a new taskbar item in the given session.
     *
     * @param sessionId session identifier.
     * @param request   payload describing the taskbar item.
     * @return created taskbar item DTO with a Location header.
     */
    // PUBLIC_INTERFACE
    @PostMapping
    @Operation(summary = "Create taskbar item", description = "Creates a new taskbar item in the given session.")
    public ResponseEntity<TaskbarItemDto> createItem(
            @PathVariable Long sessionId,
            @RequestBody TaskbarItemCreateRequest request
    ) {
        TaskbarItemDto created = taskbarItemService.createItem(sessionId, request);
        return ResponseEntity
                .created(URI.create("/api/sessions/" + sessionId + "/taskbar/" + created.id()))
                .body(created);
    }

    /**
     * Updates an existing taskbar item in the given session.
     *
     * @param sessionId session identifier.
     * @param itemId    taskbar item identifier.
     * @param request   payload describing new state.
     * @return updated taskbar item DTO.
     */
    // PUBLIC_INTERFACE
    @PutMapping("/{itemId}")
    @Operation(summary = "Update taskbar item", description = "Updates an existing taskbar item in the given session.")
    public ResponseEntity<TaskbarItemDto> updateItem(
            @PathVariable Long sessionId,
            @PathVariable Long itemId,
            @RequestBody TaskbarItemCreateRequest request
    ) {
        TaskbarItemDto updated = taskbarItemService.updateItem(sessionId, itemId, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes a taskbar item from the given session.
     *
     * @param sessionId session identifier.
     * @param itemId    taskbar item identifier.
     * @return empty response with HTTP 204 No Content on success.
     */
    // PUBLIC_INTERFACE
    @DeleteMapping("/{itemId}")
    @Operation(summary = "Delete taskbar item", description = "Deletes a taskbar item from the given session.")
    public ResponseEntity<Void> deleteItem(
            @PathVariable Long sessionId,
            @PathVariable Long itemId
    ) {
        taskbarItemService.deleteItem(sessionId, itemId);
        return ResponseEntity.noContent().build();
    }
}
