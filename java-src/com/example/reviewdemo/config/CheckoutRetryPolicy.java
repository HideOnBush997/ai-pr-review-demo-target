package com.example.reviewdemo.config;

public class CheckoutRetryPolicy {
    private final CheckoutRetryConfig config;

    public CheckoutRetryPolicy(CheckoutRetryConfig config) {
        this.config = config;
    }

    public boolean shouldRetry(int attempt, Throwable error) {
        if (attempt >= config.maxAttempts()) {
            return false;
        }
        return config.retryOnPaymentTimeout() && error.getMessage().contains("timeout");
    }
}
