package com.example.reviewdemo.platform.service;

import com.example.reviewdemo.platform.dto.PlatformRecord10;

import java.util.ArrayList;
import java.util.List;

public class PlatformRecord10Service {
    public List<PlatformRecord10> filterActive(List<PlatformRecord10> records) {
        List<PlatformRecord10> result = new ArrayList<>();
        for (PlatformRecord10 record : records) {
            if ("ACTIVE".equals(record.status())) {
                result.add(record);
            }
        }
        return result;
    }
}
