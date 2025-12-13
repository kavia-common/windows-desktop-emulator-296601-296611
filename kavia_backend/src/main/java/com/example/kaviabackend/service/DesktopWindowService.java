package com.example.kaviabackend.service;

import com.example.kaviabackend.dto.DesktopWindowCreateRequest;
import com.example.kaviabackend.dto.DesktopWindowDto;
import com.example.kaviabackend.entity.DesktopSession;
import com.example.kaviabackend.entity.DesktopWindow;
import com.example.kaviabackend.exception.ResourceNotFoundException;
import com.example.kaviabackend.repository.DesktopSessionRepository;
import com.example.kaviabackend.repository.DesktopWindowRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service providing operations for managing desktop windows.
 */
// PUBLIC_INTERFACE
@Service
public class DesktopWindowService {

    private final DesktopWindowRepository windowRepository;
    private final DesktopSessionRepository sessionRepository;

    /**
     * Creates a new {@link DesktopWindowService}.
     *
     * @param windowRepository  repository for windows.
     * @param sessionRepository repository for sessions.
     */
    // PUBLIC_INTERFACE
    public DesktopWindowService(
            DesktopWindowRepository windowRepository,
            DesktopSessionRepository sessionRepository
    ) {
        this.windowRepository = windowRepository;
        this.sessionRepository = sessionRepository;
    }

    /**
     * Lists all windows for a given session.
     *
     * @param sessionId session identifier.
     * @return list of window DTOs.
     * @throws ResourceNotFoundException if the session does not exist.
     */
    // PUBLIC_INTERFACE
    public List<DesktopWindowDto> listWindowsForSession(Long sessionId) {
        ensureSessionExists(sessionId);
        return windowRepository.findBySessionId(sessionId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new window in the given session.
     *
     * @param sessionId session identifier.
     * @param request   payload describing the window.
     * @return created window DTO.
     * @throws ResourceNotFoundException if the session does not exist.
     */
    // PUBLIC_INTERFACE
    public DesktopWindowDto createWindow(Long sessionId, DesktopWindowCreateRequest request) {
        DesktopSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Desktop session not found: " + sessionId));

        DesktopWindow window = new DesktopWindow(
                session,
                request.title(),
                request.x(),
                request.y(),
                request.width(),
                request.height(),
                request.zIndex(),
                request.state(),
                request.payload()
        );

        DesktopWindow saved = windowRepository.save(window);
        return toDto(saved);
    }

    /**
     * Updates an existing window in the given session.
     *
     * @param sessionId session identifier.
     * @param windowId  window identifier.
     * @param request   payload describing new window state.
     * @return updated window DTO.
     * @throws ResourceNotFoundException if either the session or window does not exist or do not match.
     */
    // PUBLIC_INTERFACE
    public DesktopWindowDto updateWindow(Long sessionId, Long windowId, DesktopWindowCreateRequest request) {
        ensureSessionExists(sessionId);
        DesktopWindow window = windowRepository.findById(windowId)
                .orElseThrow(() -> new ResourceNotFoundException("Desktop window not found: " + windowId));

        if (!window.getSession().getId().equals(sessionId)) {
            throw new ResourceNotFoundException("Desktop window does not belong to session: " + sessionId);
        }

        window.setTitle(request.title());
        window.setX(request.x());
        window.setY(request.y());
        window.setWidth(request.width());
        window.setHeight(request.height());
        window.setZIndex(request.zIndex());
        window.setState(request.state());
        window.setPayload(request.payload());

        DesktopWindow saved = windowRepository.save(window);
        return toDto(saved);
    }

    /**
     * Deletes a window from the given session.
     *
     * @param sessionId session identifier.
     * @param windowId  window identifier.
     * @throws ResourceNotFoundException if the window does not exist or does not belong to the session.
     */
    // PUBLIC_INTERFACE
    public void deleteWindow(Long sessionId, Long windowId) {
        ensureSessionExists(sessionId);
        DesktopWindow window = windowRepository.findById(windowId)
                .orElseThrow(() -> new ResourceNotFoundException("Desktop window not found: " + windowId));

        if (!window.getSession().getId().equals(sessionId)) {
            throw new ResourceNotFoundException("Desktop window does not belong to session: " + sessionId);
        }

        windowRepository.delete(window);
    }

    private void ensureSessionExists(Long sessionId) {
        if (!sessionRepository.existsById(sessionId)) {
            throw new ResourceNotFoundException("Desktop session not found: " + sessionId);
        }
    }

    private DesktopWindowDto toDto(DesktopWindow window) {
        return new DesktopWindowDto(
            window.getId(),
            window.getSession().getId(),
            window.getTitle(),
            window.getX(),
            window.getY(),
            window.getWidth(),
            window.getHeight(),
            window.getZIndex(),
            window.getState(),
            window.getPayload(),
            window.getCreatedAt(),
            window.getUpdatedAt()
        );
    }
}
