package com.example.kaviabackend.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * JPA entity representing an icon on the desktop for a given session.
 */
@Entity
@Table(name = "desktop_icon")
// PUBLIC_INTERFACE
public class DesktopIcon {

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
    protected DesktopIcon() {
        // For JPA
    }

    /**
     * Constructs a new desktop icon for the given session.
     *
     * @param session desktop session that owns this icon.
     * @param title   display title of the icon.
     * @param x       horizontal position.
     * @param y       vertical position.
     * @param payload optional JSON payload with additional metadata.
     */
    // PUBLIC_INTERFACE
    public DesktopIcon(DesktopSession session, String title, int x, int y, JsonNode payload) {
        this.session = session;
        this.title = title;
        this.x = x;
        this.y = y;
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
