package com.example.kaviabackend.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA entity representing a desktop session, which owns icons, windows, and taskbar items.
 */
@Entity
@Table(name = "desktop_session")
// PUBLIC_INTERFACE
public class DesktopSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DesktopIcon> icons = new ArrayList<>();

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DesktopWindow> windows = new ArrayList<>();

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskbarItem> taskbarItems = new ArrayList<>();

    /**
     * Protected no-arg constructor required by JPA.
     */
    protected DesktopSession() {
        // For JPA
    }

    /**
     * Constructs a new desktop session with the given name.
     *
     * @param name human-readable name of the session.
     */
    // PUBLIC_INTERFACE
    public DesktopSession(String name) {
        this.name = name;
    }

    @PrePersist
    void onCreate() {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = OffsetDateTime.now(ZoneOffset.UTC);
    }

    // PUBLIC_INTERFACE
    public Long getId() {
        return id;
    }

    // PUBLIC_INTERFACE
    public String getName() {
        return name;
    }

    // PUBLIC_INTERFACE
    public void setName(String name) {
        this.name = name;
    }

    // PUBLIC_INTERFACE
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    // PUBLIC_INTERFACE
    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    // PUBLIC_INTERFACE
    public List<DesktopIcon> getIcons() {
        return icons;
    }

    // PUBLIC_INTERFACE
    public List<DesktopWindow> getWindows() {
        return windows;
    }

    // PUBLIC_INTERFACE
    public List<TaskbarItem> getTaskbarItems() {
        return taskbarItems;
    }
}
