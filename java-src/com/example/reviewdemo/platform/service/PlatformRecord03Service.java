package com.example.reviewdemo.platform.service;

import com.example.reviewdemo.platform.dto.PlatformRecord03;

import java.util.ArrayList;
import java.util.List;

public class PlatformRecord03Service {
    public List<PlatformRecord03> filterActive(List<PlatformRecord03> records) {
        List<PlatformRecord03> result = new ArrayList<>();
        for (PlatformRecord03 record : records) {
            if ("ACTIVE".equals(record.status())) {
                result.add(record);
            }
        }
        return result;
    }
}
