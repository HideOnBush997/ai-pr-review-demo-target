package com.example.reviewdemo.config;

public record CheckoutRetryConfig(
        int maxAttempts,
        int timeoutMillis,
        int backoffMillis,
        boolean retryOnPaymentTimeout
) {
}
