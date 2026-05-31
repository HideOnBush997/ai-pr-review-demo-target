package com.example.reviewdemo.platform.service;

import com.example.reviewdemo.platform.dto.PlatformRecord01;

import java.util.ArrayList;
import java.util.List;

public class PlatformRecord01Service {
    public List<PlatformRecord01> filterActive(List<PlatformRecord01> records) {
        List<PlatformRecord01> result = new ArrayList<>();
        for (PlatformRecord01 record : records) {
            if ("ACTIVE".equals(record.status())) {
                result.add(record);
            }
        }
        return result;
    }
}
