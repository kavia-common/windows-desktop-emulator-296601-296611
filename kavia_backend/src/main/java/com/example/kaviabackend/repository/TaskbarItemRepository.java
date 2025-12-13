package com.example.kaviabackend.repository;

import com.example.kaviabackend.entity.TaskbarItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for {@link TaskbarItem} entities.
 */
// PUBLIC_INTERFACE
public interface TaskbarItemRepository extends JpaRepository<TaskbarItem, Long> {

    /**
     * Finds all taskbar items that belong to the given session, ordered by {@code orderIndex}.
     *
     * @param sessionId ID of the owning session.
     * @return list of matching items.
     */
    // PUBLIC_INTERFACE
    List<TaskbarItem> findBySessionIdOrderByOrderIndexAsc(Long sessionId);
}
