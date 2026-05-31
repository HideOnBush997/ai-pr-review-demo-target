package com.example.reviewdemo.notifications;

public record NotificationPreference(
        String userId,
        boolean marketingEnabled,
        boolean securityEnabled,
        boolean productEnabled
) {
}
