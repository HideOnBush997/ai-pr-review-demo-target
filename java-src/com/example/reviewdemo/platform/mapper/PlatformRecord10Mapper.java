package com.example.reviewdemo.platform.mapper;

import com.example.reviewdemo.platform.dto.PlatformRecord10;

import java.time.Instant;
import java.util.Map;

public class PlatformRecord10Mapper {
    public PlatformRecord10 fromMap(Map<String, String> row) {
        return new PlatformRecord10(
                row.get("id"),
                row.get("tenant_id"),
                row.get("owner_id"),
                row.getOrDefault("status", "ACTIVE"),
                Instant.parse(row.get("updated_at"))
        );
    }
}
