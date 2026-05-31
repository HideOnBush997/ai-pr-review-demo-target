package com.example.reviewdemo.platform.service;

import com.example.reviewdemo.platform.dto.PlatformRecord07;

import java.util.ArrayList;
import java.util.List;

public class PlatformRecord07Service {
    public List<PlatformRecord07> filterActive(List<PlatformRecord07> records) {
        List<PlatformRecord07> result = new ArrayList<>();
        for (PlatformRecord07 record : records) {
            if ("ACTIVE".equals(record.status())) {
                result.add(record);
            }
        }
        return result;
    }
}
