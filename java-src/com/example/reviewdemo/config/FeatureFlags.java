package com.example.reviewdemo.config;

import java.util.Map;

public class FeatureFlags {
    private final Map<String, Boolean> values;

    public FeatureFlags(Map<String, Boolean> values) {
        this.values = values;
    }

    public boolean isEnabled(String key) {
        return values.getOrDefault(key, false);
    }
}
