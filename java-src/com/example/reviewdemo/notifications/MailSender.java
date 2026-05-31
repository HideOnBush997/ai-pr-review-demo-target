package com.example.reviewdemo.notifications;

public interface MailSender {
    void send(String userId, String subject, String body);
}
