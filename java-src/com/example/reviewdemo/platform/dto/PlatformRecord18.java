package com.example.reviewdemo.platform.dto;

import java.time.Instant;

public record PlatformRecord18(
        String id,
        String tenantId,
        String ownerId,
        String status,
        Instant updatedAt
) {
}
