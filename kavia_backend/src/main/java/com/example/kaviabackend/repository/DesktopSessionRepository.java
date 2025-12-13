package com.example.kaviabackend.repository;

import com.example.kaviabackend.entity.DesktopSession;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link DesktopSession} entities.
 */
// PUBLIC_INTERFACE
public interface DesktopSessionRepository extends JpaRepository<DesktopSession, Long> {
}
