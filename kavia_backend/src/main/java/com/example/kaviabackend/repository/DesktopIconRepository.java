package com.example.kaviabackend.repository;

import com.example.kaviabackend.entity.DesktopIcon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for {@link DesktopIcon} entities.
 */
// PUBLIC_INTERFACE
public interface DesktopIconRepository extends JpaRepository<DesktopIcon, Long> {

    /**
     * Finds all icons that belong to the given session.
     *
     * @param sessionId ID of the owning session.
     * @return list of matching icons.
     */
    // PUBLIC_INTERFACE
    List<DesktopIcon> findBySessionId(Long sessionId);
}
