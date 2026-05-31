package com.example.reviewdemo.platform.mapper;

import com.example.reviewdemo.platform.dto.PlatformRecord11;

import java.time.Instant;
import java.util.Map;

public class PlatformRecord11Mapper {
    public PlatformRecord11 fromMap(Map<String, String> row) {
        return new PlatformRecord11(
                row.get("id"),
                row.get("tenant_id"),
                row.get("owner_id"),
                row.getOrDefault("status", "ACTIVE"),
                Instant.parse(row.get("updated_at"))
        );
    }
}
