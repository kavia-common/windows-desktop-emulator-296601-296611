package com.example.kaviabackend.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * JPA entity representing a taskbar item for a given session.
 */
@Entity
@Table(name = "taskbar_item")
// PUBLIC_INTERFACE
public class TaskbarItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    private DesktopSession session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "window_id")
    private DesktopWindow window;

    @Column(name = "label", nullable = false, length = 255)
    private String label;

    @Column(name = "pinned", nullable = false)
    private boolean pinned;

    @Column(name = "order_index", nullable = false)
    private int orderIndex;

    @Column(name = "payload", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode payload;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    /**
     * Protected no-arg constructor required by JPA.
     */
    protected TaskbarItem() {
        // For JPA
    }

    /**
     * Constructs a new taskbar item.
     *
     * @param session    owning session.
     * @param window     associated window, if any (may be {@code null}).
     * @param label      label shown in the taskbar.
     * @param pinned     whether the item is pinned.
     * @param orderIndex ordering index within the taskbar.
     * @param payload    optional JSON payload.
     */
    // PUBLIC_INTERFACE
    public TaskbarItem(
            DesktopSession session,
            DesktopWindow window,
            String label,
            boolean pinned,
            int orderIndex,
            JsonNode payload
    ) {
        this.session = session;
        this.window = window;
        this.label = label;
        this.pinned = pinned;
        this.orderIndex = orderIndex;
        this.payload = payload;
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
    public DesktopSession getSession() {
        return session;
    }

    // PUBLIC_INTERFACE
    public void setSession(DesktopSession session) {
        this.session = session;
    }

    // PUBLIC_INTERFACE
    public DesktopWindow getWindow() {
        return window;
    }

    // PUBLIC_INTERFACE
    public void setWindow(DesktopWindow window) {
        this.window = window;
    }

    // PUBLIC_INTERFACE
    public String getLabel() {
        return label;
    }

    // PUBLIC_INTERFACE
    public void setLabel(String label) {
        this.label = label;
    }

    // PUBLIC_INTERFACE
    public boolean isPinned() {
        return pinned;
    }

    // PUBLIC_INTERFACE
    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    // PUBLIC_INTERFACE
    public int getOrderIndex() {
        return orderIndex;
    }

    // PUBLIC_INTERFACE
    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    // PUBLIC_INTERFACE
    public JsonNode getPayload() {
        return payload;
    }

    // PUBLIC_INTERFACE
    public void setPayload(JsonNode payload) {
        this.payload = payload;
    }

    // PUBLIC_INTERFACE
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    // PUBLIC_INTERFACE
    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}
