package com.example.kaviabackend.service;

import com.example.kaviabackend.dto.DesktopSessionCreateRequest;
import com.example.kaviabackend.dto.DesktopSessionDto;
import com.example.kaviabackend.entity.DesktopSession;
import com.example.kaviabackend.exception.ResourceNotFoundException;
import com.example.kaviabackend.repository.DesktopSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service providing operations for managing desktop sessions.
 */
// PUBLIC_INTERFACE
@Service
public class DesktopSessionService {

    private final DesktopSessionRepository sessionRepository;

    /**
     * Creates a new {@link DesktopSessionService}.
     *
     * @param sessionRepository repository for persisting sessions.
     */
    // PUBLIC_INTERFACE
    public DesktopSessionService(DesktopSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    /**
     * Returns all desktop sessions.
     *
     * @return list of session DTOs.
     */
    // PUBLIC_INTERFACE
    public List<DesktopSessionDto> getAllSessions() {
        return sessionRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Returns a single desktop session by its ID.
     *
     * @param id session identifier.
     * @return session DTO.
     * @throws ResourceNotFoundException if no session exists with the given ID.
     */
    // PUBLIC_INTERFACE
    public DesktopSessionDto getSession(Long id) {
        DesktopSession session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Desktop session not found: " + id));
        return toDto(session);
    }

    /**
     * Creates a new desktop session.
     *
     * @param request payload describing the session to create.
     * @return created session DTO.
     */
    // PUBLIC_INTERFACE
    public DesktopSessionDto createSession(DesktopSessionCreateRequest request) {
        DesktopSession session = new DesktopSession(request.name());
        DesktopSession saved = sessionRepository.save(session);
        return toDto(saved);
    }

    /**
     * Updates the name of an existing desktop session.
     *
     * @param id      session identifier.
     * @param request payload containing the new name.
     * @return updated session DTO.
     * @throws ResourceNotFoundException if the session does not exist.
     */
    // PUBLIC_INTERFACE
    public DesktopSessionDto updateSession(Long id, DesktopSessionCreateRequest request) {
        DesktopSession session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Desktop session not found: " + id));
        session.setName(request.name());
        DesktopSession saved = sessionRepository.save(session);
        return toDto(saved);
    }

    /**
     * Deletes a desktop session by ID.
     *
     * @param id session identifier.
     * @throws ResourceNotFoundException if the session does not exist.
     */
    // PUBLIC_INTERFACE
    public void deleteSession(Long id) {
        if (!sessionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Desktop session not found: " + id);
        }
        sessionRepository.deleteById(id);
    }

    private DesktopSessionDto toDto(DesktopSession session) {
        return new DesktopSessionDto(
                session.getId(),
                session.getName(),
                session.getCreatedAt(),
                session.getUpdatedAt()
        );
    }
}
