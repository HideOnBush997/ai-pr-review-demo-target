package com.example.reviewdemo.platform.service;

import com.example.reviewdemo.platform.dto.PlatformRecord05;

import java.util.ArrayList;
import java.util.List;

public class PlatformRecord05Service {
    public List<PlatformRecord05> filterActive(List<PlatformRecord05> records) {
        List<PlatformRecord05> result = new ArrayList<>();
        for (PlatformRecord05 record : records) {
            if ("ACTIVE".equals(record.status())) {
                result.add(record);
            }
        }
        return result;
    }
}
