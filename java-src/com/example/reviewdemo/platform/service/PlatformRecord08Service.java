package com.example.reviewdemo.platform.service;

import com.example.reviewdemo.platform.dto.PlatformRecord08;

import java.util.ArrayList;
import java.util.List;

public class PlatformRecord08Service {
    public List<PlatformRecord08> filterActive(List<PlatformRecord08> records) {
        List<PlatformRecord08> result = new ArrayList<>();
        for (PlatformRecord08 record : records) {
            if ("ACTIVE".equals(record.status())) {
                result.add(record);
            }
        }
        return result;
    }
}
