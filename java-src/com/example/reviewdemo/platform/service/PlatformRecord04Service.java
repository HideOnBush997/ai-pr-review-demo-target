package com.example.reviewdemo.platform.service;

import com.example.reviewdemo.platform.dto.PlatformRecord04;

import java.util.ArrayList;
import java.util.List;

public class PlatformRecord04Service {
    public List<PlatformRecord04> filterActive(List<PlatformRecord04> records) {
        List<PlatformRecord04> result = new ArrayList<>();
        for (PlatformRecord04 record : records) {
            if ("ACTIVE".equals(record.status())) {
                result.add(record);
            }
        }
        return result;
    }
}
