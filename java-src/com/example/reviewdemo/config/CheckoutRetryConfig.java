package com.example.reviewdemo.config;

public record CheckoutRetryConfig(
        int maxAttempts,
        int timeoutMillis,
        boolean retryOnPaymentTimeout
) {
}
