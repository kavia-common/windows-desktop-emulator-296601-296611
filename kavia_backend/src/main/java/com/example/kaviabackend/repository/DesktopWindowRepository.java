package com.example.kaviabackend.repository;

import com.example.kaviabackend.entity.DesktopWindow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for {@link DesktopWindow} entities.
 */
// PUBLIC_INTERFACE
public interface DesktopWindowRepository extends JpaRepository<DesktopWindow, Long> {

    /**
     * Finds all windows that belong to the given session.
     *
     * @param sessionId ID of the owning session.
     * @return list of matching windows.
     */
    // PUBLIC_INTERFACE
    List<DesktopWindow> findBySessionId(Long sessionId);
}
