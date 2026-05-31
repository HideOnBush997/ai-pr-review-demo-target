package com.example.reviewdemo.platform.service;

import com.example.reviewdemo.platform.dto.PlatformRecord09;

import java.util.ArrayList;
import java.util.List;

public class PlatformRecord09Service {
    public List<PlatformRecord09> filterActive(List<PlatformRecord09> records) {
        List<PlatformRecord09> result = new ArrayList<>();
        for (PlatformRecord09 record : records) {
            if ("ACTIVE".equals(record.status())) {
                result.add(record);
            }
        }
        return result;
    }
}
