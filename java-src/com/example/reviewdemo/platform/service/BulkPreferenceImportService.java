package com.example.reviewdemo.platform.service;

import com.example.reviewdemo.notifications.NotificationPreference;
import com.example.reviewdemo.notifications.NotificationPreferenceRepository;

import java.util.List;

public class BulkPreferenceImportService {
    private final NotificationPreferenceRepository repository;

    public BulkPreferenceImportService(NotificationPreferenceRepository repository) {
        this.repository = repository;
    }

    public int importMissingPreferences(List<String> userIds) {
        int imported = 0;
        for (String userId : userIds) {
            if (repository.findByUserId(userId).isEmpty()) {
                repository.save(new NotificationPreference(
                        userId,
                        true,
                        true,
                        true
                ));
                imported++;
            }
        }
        return imported;
    }
}
