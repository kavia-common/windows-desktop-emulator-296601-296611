package com.example.kaviabackend.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * JPA entity representing a window on the desktop for a given session.
 */
@Entity
@Table(name = "desktop_window")
// PUBLIC_INTERFACE
public class DesktopWindow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    private DesktopSession session;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "x", nullable = false)
    private int x;

    @Column(name = "y", nullable = false)
    private int y;

    @Column(name = "width", nullable = false)
    private int width;

    @Column(name = "height", nullable = false)
    private int height;

    @Column(name = "z_index", nullable = false)
    private int zIndex;

    @Column(name = "state", nullable = false, length = 50)
    private String state;

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
    protected DesktopWindow() {
        // For JPA
    }

    /**
     * Constructs a new desktop window.
     *
     * @param session owner session.
     * @param title   window title.
     * @param x       horizontal position.
     * @param y       vertical position.
     * @param width   width in pixels.
     * @param height  height in pixels.
     * @param zIndex  stacking order index.
     * @param state   string representing the window state (e.g. "normal", "minimized", "maximized").
     * @param payload optional JSON payload.
     */
    // PUBLIC_INTERFACE
    public DesktopWindow(
            DesktopSession session,
            String title,
            int x,
            int y,
            int width,
            int height,
            int zIndex,
            String state,
            JsonNode payload
    ) {
        this.session = session;
        this.title = title;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.zIndex = zIndex;
        this.state = state;
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
    public String getTitle() {
        return title;
    }

    // PUBLIC_INTERFACE
    public void setTitle(String title) {
        this.title = title;
    }

    // PUBLIC_INTERFACE
    public int getX() {
        return x;
    }

    // PUBLIC_INTERFACE
    public void setX(int x) {
        this.x = x;
    }

    // PUBLIC_INTERFACE
    public int getY() {
        return y;
    }

    // PUBLIC_INTERFACE
    public void setY(int y) {
        this.y = y;
    }

    // PUBLIC_INTERFACE
    public int getWidth() {
        return width;
    }

    // PUBLIC_INTERFACE
    public void setWidth(int width) {
        this.width = width;
    }

    // PUBLIC_INTERFACE
    public int getHeight() {
        return height;
    }

    // PUBLIC_INTERFACE
    public void setHeight(int height) {
        this.height = height;
    }

    // PUBLIC_INTERFACE
    public int getZIndex() {
        return zIndex;
    }

    // PUBLIC_INTERFACE
    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    // PUBLIC_INTERFACE
    public String getState() {
        return state;
    }

    // PUBLIC_INTERFACE
    public void setState(String state) {
        this.state = state;
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
