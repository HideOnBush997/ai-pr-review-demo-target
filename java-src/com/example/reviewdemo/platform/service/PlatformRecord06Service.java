package com.example.reviewdemo.platform.service;

import com.example.reviewdemo.platform.dto.PlatformRecord06;

import java.util.ArrayList;
import java.util.List;

public class PlatformRecord06Service {
    public List<PlatformRecord06> filterActive(List<PlatformRecord06> records) {
        List<PlatformRecord06> result = new ArrayList<>();
        for (PlatformRecord06 record : records) {
            if ("ACTIVE".equals(record.status())) {
                result.add(record);
            }
        }
        return result;
    }
}
