package com.example.reviewdemo.notifications;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class NotificationPreferenceRepository {
    private final Map<String, NotificationPreference> preferences = new ConcurrentHashMap<>();

    public Optional<NotificationPreference> findByUserId(String userId) {
        return Optional.ofNullable(preferences.get(userId));
    }

    public void save(NotificationPreference preference) {
        preferences.put(preference.userId(), preference);
    }
}
