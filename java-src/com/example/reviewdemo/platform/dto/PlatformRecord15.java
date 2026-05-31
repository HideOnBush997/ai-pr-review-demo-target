package com.example.reviewdemo.platform.dto;

import java.time.Instant;

public record PlatformRecord15(
        String id,
        String tenantId,
        String ownerId,
        String status,
        Instant updatedAt
) {
}
