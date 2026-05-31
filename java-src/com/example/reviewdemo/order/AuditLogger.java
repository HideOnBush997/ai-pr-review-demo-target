package com.example.reviewdemo.order;

public interface AuditLogger {
    void paymentSucceeded(String orderId, String paymentId);

    void paymentFailed(String orderId, String reason);
}
