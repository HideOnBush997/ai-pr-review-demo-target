package com.example.reviewdemo.platform.mapper;

import com.example.reviewdemo.platform.dto.PlatformRecord03;

import java.time.Instant;
import java.util.Map;

public class PlatformRecord03Mapper {
    public PlatformRecord03 fromMap(Map<String, String> row) {
        return new PlatformRecord03(
                row.get("id"),
                row.get("tenant_id"),
                row.get("owner_id"),
                row.getOrDefault("status", "ACTIVE"),
                Instant.parse(row.get("updated_at"))
        );
    }
}
