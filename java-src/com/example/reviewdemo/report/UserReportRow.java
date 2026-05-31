package com.example.reviewdemo.report;

import java.time.Instant;

public record UserReportRow(
        String tenantId,
        String userId,
        String email,
        String status,
        Instant createdAt
) {
}
