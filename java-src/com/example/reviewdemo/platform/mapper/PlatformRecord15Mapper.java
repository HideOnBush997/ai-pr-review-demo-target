package com.example.reviewdemo.platform.mapper;

import com.example.reviewdemo.platform.dto.PlatformRecord15;

import java.time.Instant;
import java.util.Map;

public class PlatformRecord15Mapper {
    public PlatformRecord15 fromMap(Map<String, String> row) {
        return new PlatformRecord15(
                row.get("id"),
                row.get("tenant_id"),
                row.get("owner_id"),
                row.getOrDefault("status", "ACTIVE"),
                Instant.parse(row.get("updated_at"))
        );
    }
}
