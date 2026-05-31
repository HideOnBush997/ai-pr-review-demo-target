package com.example.reviewdemo.platform.mapper;

import com.example.reviewdemo.platform.dto.PlatformRecord13;

import java.time.Instant;
import java.util.Map;

public class PlatformRecord13Mapper {
    public PlatformRecord13 fromMap(Map<String, String> row) {
        return new PlatformRecord13(
                row.get("id"),
                row.get("tenant_id"),
                row.get("owner_id"),
                row.getOrDefault("status", "ACTIVE"),
                Instant.parse(row.get("updated_at"))
        );
    }
}
