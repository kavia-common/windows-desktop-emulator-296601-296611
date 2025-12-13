package com.example.kaviabackend.service;

import com.example.kaviabackend.dto.DesktopIconCreateRequest;
import com.example.kaviabackend.dto.DesktopIconDto;
import com.example.kaviabackend.entity.DesktopIcon;
import com.example.kaviabackend.entity.DesktopSession;
import com.example.kaviabackend.exception.ResourceNotFoundException;
import com.example.kaviabackend.repository.DesktopIconRepository;
import com.example.kaviabackend.repository.DesktopSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service providing operations for managing desktop icons.
 */
// PUBLIC_INTERFACE
@Service
public class DesktopIconService {

    private final DesktopIconRepository iconRepository;
    private final DesktopSessionRepository sessionRepository;

    /**
     * Creates a new {@link DesktopIconService}.
     *
     * @param iconRepository    repository for icons.
     * @param sessionRepository repository for sessions.
     */
    // PUBLIC_INTERFACE
    public DesktopIconService(
            DesktopIconRepository iconRepository,
            DesktopSessionRepository sessionRepository
    ) {
        this.iconRepository = iconRepository;
        this.sessionRepository = sessionRepository;
    }

    /**
     * Lists all icons for a given session.
     *
     * @param sessionId session identifier.
     * @return list of icon DTOs.
     * @throws ResourceNotFoundException if the session does not exist.
     */
    // PUBLIC_INTERFACE
    public List<DesktopIconDto> listIconsForSession(Long sessionId) {
        ensureSessionExists(sessionId);
        return iconRepository.findBySessionId(sessionId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new icon in the given session.
     *
     * @param sessionId session identifier.
     * @param request   payload describing the icon.
     * @return created icon DTO.
     * @throws ResourceNotFoundException if the session does not exist.
     */
    // PUBLIC_INTERFACE
    public DesktopIconDto createIcon(Long sessionId, DesktopIconCreateRequest request) {
        DesktopSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Desktop session not found: " + sessionId));

        DesktopIcon icon = new DesktopIcon(
                session,
                request.title(),
                request.x(),
                request.y(),
                request.payload()
        );

        DesktopIcon saved = iconRepository.save(icon);
        return toDto(saved);
    }

    /**
     * Updates an existing icon in the given session.
     *
     * @param sessionId session identifier.
     * @param iconId    icon identifier.
     * @param request   payload describing new icon state.
     * @return updated icon DTO.
     * @throws ResourceNotFoundException if either the session or the icon does not exist or do not match.
     */
    // PUBLIC_INTERFACE
    public DesktopIconDto updateIcon(Long sessionId, Long iconId, DesktopIconCreateRequest request) {
        ensureSessionExists(sessionId);
        DesktopIcon icon = iconRepository.findById(iconId)
                .orElseThrow(() -> new ResourceNotFoundException("Desktop icon not found: " + iconId));

        if (!icon.getSession().getId().equals(sessionId)) {
            throw new ResourceNotFoundException("Desktop icon does not belong to session: " + sessionId);
        }

        icon.setTitle(request.title());
        icon.setX(request.x());
        icon.setY(request.y());
        icon.setPayload(request.payload());

        DesktopIcon saved = iconRepository.save(icon);
        return toDto(saved);
    }

    /**
     * Deletes an icon from the given session.
     *
     * @param sessionId session identifier.
     * @param iconId    icon identifier.
     * @throws ResourceNotFoundException if the icon does not exist or does not belong to the session.
     */
    // PUBLIC_INTERFACE
    public void deleteIcon(Long sessionId, Long iconId) {
        ensureSessionExists(sessionId);
        DesktopIcon icon = iconRepository.findById(iconId)
                .orElseThrow(() -> new ResourceNotFoundException("Desktop icon not found: " + iconId));

        if (!icon.getSession().getId().equals(sessionId)) {
            throw new ResourceNotFoundException("Desktop icon does not belong to session: " + sessionId);
        }

        iconRepository.delete(icon);
    }

    private void ensureSessionExists(Long sessionId) {
        if (!sessionRepository.existsById(sessionId)) {
            throw new ResourceNotFoundException("Desktop session not found: " + sessionId);
        }
    }

    private DesktopIconDto toDto(DesktopIcon icon) {
        return new DesktopIconDto(
                icon.getId(),
                icon.getSession().getId(),
                icon.getTitle(),
                icon.getX(),
                icon.getY(),
                icon.getPayload(),
                icon.getCreatedAt(),
                icon.getUpdatedAt()
        );
    }
}
