package com.example.reviewdemo.auth;

import java.time.Instant;
import java.util.Objects;

public final class Session {
    private final String userId;
    private final String role;
    private final String tenantId;
    private final String accessToken;
    private final Instant expiresAt;

    public Session(String userId, String role, String tenantId, String accessToken, Instant expiresAt) {
        this.userId = Objects.requireNonNull(userId);
        this.role = Objects.requireNonNull(role);
        this.tenantId = Objects.requireNonNull(tenantId);
        this.accessToken = Objects.requireNonNull(accessToken);
        this.expiresAt = Objects.requireNonNull(expiresAt);
    }

    public String userId() {
        return userId;
    }

    public String role() {
        return role;
    }

    public String tenantId() {
        return tenantId;
    }

    public String accessToken() {
        return accessToken;
    }

    public Instant expiresAt() {
        return expiresAt;
    }

    public boolean isExpired(Instant now) {
        return !expiresAt.isAfter(now);
    }

    public boolean isAdmin() {
        return "ADMIN".equals(role);
    }
}
