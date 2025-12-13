package com.example.kaviabackend.service;

import com.example.kaviabackend.dto.TaskbarItemCreateRequest;
import com.example.kaviabackend.dto.TaskbarItemDto;
import com.example.kaviabackend.entity.DesktopSession;
import com.example.kaviabackend.entity.DesktopWindow;
import com.example.kaviabackend.entity.TaskbarItem;
import com.example.kaviabackend.exception.ResourceNotFoundException;
import com.example.kaviabackend.repository.DesktopSessionRepository;
import com.example.kaviabackend.repository.DesktopWindowRepository;
import com.example.kaviabackend.repository.TaskbarItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service providing operations for managing taskbar items.
 */
// PUBLIC_INTERFACE
@Service
public class TaskbarItemService {

    private final TaskbarItemRepository taskbarItemRepository;
    private final DesktopSessionRepository sessionRepository;
    private final DesktopWindowRepository windowRepository;

    /**
     * Creates a new {@link TaskbarItemService}.
     *
     * @param taskbarItemRepository repository for taskbar items.
     * @param sessionRepository     repository for sessions.
     * @param windowRepository      repository for windows.
     */
    // PUBLIC_INTERFACE
    public TaskbarItemService(
            TaskbarItemRepository taskbarItemRepository,
            DesktopSessionRepository sessionRepository,
            DesktopWindowRepository windowRepository
    ) {
        this.taskbarItemRepository = taskbarItemRepository;
        this.sessionRepository = sessionRepository;
        this.windowRepository = windowRepository;
    }

    /**
     * Lists all taskbar items for a given session, ordered by {@code orderIndex}.
     *
     * @param sessionId session identifier.
     * @return list of taskbar item DTOs.
     * @throws ResourceNotFoundException if the session does not exist.
     */
    // PUBLIC_INTERFACE
    public List<TaskbarItemDto> listItemsForSession(Long sessionId) {
        ensureSessionExists(sessionId);
        return taskbarItemRepository.findBySessionIdOrderByOrderIndexAsc(sessionId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new taskbar item in the given session.
     *
     * @param sessionId session identifier.
     * @param request   payload describing the taskbar item.
     * @return created taskbar item DTO.
     * @throws ResourceNotFoundException if the session or referenced window does not exist.
     */
    // PUBLIC_INTERFACE
    public TaskbarItemDto createItem(Long sessionId, TaskbarItemCreateRequest request) {
        DesktopSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Desktop session not found: " + sessionId));

        DesktopWindow window = null;
        if (request.windowId() != null) {
            window = windowRepository.findById(request.windowId())
                    .orElseThrow(() -> new ResourceNotFoundException("Desktop window not found: " + request.windowId()));
        }

        TaskbarItem item = new TaskbarItem(
                session,
                window,
                request.label(),
                request.pinned(),
                request.orderIndex(),
                request.payload()
        );

        TaskbarItem saved = taskbarItemRepository.save(item);
        return toDto(saved);
    }

    /**
     * Updates an existing taskbar item in the given session.
     *
     * @param sessionId session identifier.
     * @param itemId    taskbar item identifier.
     * @param request   payload describing the new state.
     * @return updated taskbar item DTO.
     * @throws ResourceNotFoundException if the session or item does not exist or do not match.
     */
    // PUBLIC_INTERFACE
    public TaskbarItemDto updateItem(Long sessionId, Long itemId, TaskbarItemCreateRequest request) {
        ensureSessionExists(sessionId);
        TaskbarItem item = taskbarItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Taskbar item not found: " + itemId));

        if (!item.getSession().getId().equals(sessionId)) {
            throw new ResourceNotFoundException("Taskbar item does not belong to session: " + sessionId);
        }

        DesktopWindow window = null;
        if (request.windowId() != null) {
            window = windowRepository.findById(request.windowId())
                    .orElseThrow(() -> new ResourceNotFoundException("Desktop window not found: " + request.windowId()));
        }

        item.setWindow(window);
        item.setLabel(request.label());
        item.setPinned(request.pinned());
        item.setOrderIndex(request.orderIndex());
        item.setPayload(request.payload());

        TaskbarItem saved = taskbarItemRepository.save(item);
        return toDto(saved);
    }

    /**
     * Deletes a taskbar item from the given session.
     *
     * @param sessionId session identifier.
     * @param itemId    taskbar item identifier.
     * @throws ResourceNotFoundException if the item does not exist or does not belong to the session.
     */
    // PUBLIC_INTERFACE
    public void deleteItem(Long sessionId, Long itemId) {
        ensureSessionExists(sessionId);
        TaskbarItem item = taskbarItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Taskbar item not found: " + itemId));

        if (!item.getSession().getId().equals(sessionId)) {
            throw new ResourceNotFoundException("Taskbar item does not belong to session: " + sessionId);
        }

        taskbarItemRepository.delete(item);
    }

    private void ensureSessionExists(Long sessionId) {
        if (!sessionRepository.existsById(sessionId)) {
            throw new ResourceNotFoundException("Desktop session not found: " + sessionId);
        }
    }

    private TaskbarItemDto toDto(TaskbarItem item) {
        Long windowId = item.getWindow() != null ? item.getWindow().getId() : null;
        return new TaskbarItemDto(
                item.getId(),
                item.getSession().getId(),
                windowId,
                item.getLabel(),
                item.isPinned(),
                item.getOrderIndex(),
                item.getPayload(),
                item.getCreatedAt(),
                item.getUpdatedAt()
        );
    }
}
