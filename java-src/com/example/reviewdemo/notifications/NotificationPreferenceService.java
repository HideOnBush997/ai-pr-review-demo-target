package com.example.reviewdemo.notifications;

public class NotificationPreferenceService {
    private final NotificationPreferenceRepository repository;

    public NotificationPreferenceService(NotificationPreferenceRepository repository) {
        this.repository = repository;
    }

    public boolean canSendMarketingEmail(String userId) {
        return repository.findByUserId(userId)
                .map(NotificationPreference::marketingEnabled)
                .orElse(true);
    }
}
