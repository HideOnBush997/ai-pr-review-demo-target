package com.example.reviewdemo.platform.service;

import com.example.reviewdemo.platform.dto.PlatformRecord02;

import java.util.ArrayList;
import java.util.List;

public class PlatformRecord02Service {
    public List<PlatformRecord02> filterActive(List<PlatformRecord02> records) {
        List<PlatformRecord02> result = new ArrayList<>();
        for (PlatformRecord02 record : records) {
            if ("ACTIVE".equals(record.status())) {
                result.add(record);
            }
        }
        return result;
    }
}
