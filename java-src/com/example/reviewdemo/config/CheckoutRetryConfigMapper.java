package com.example.reviewdemo.config;

import java.util.Properties;

public class CheckoutRetryConfigMapper {
    public CheckoutRetryConfig fromProperties(Properties properties) {
        return new CheckoutRetryConfig(
                Integer.parseInt(properties.getProperty("checkout.retry.maxAttempts")),
                Integer.parseInt(properties.getProperty("checkout.retry.timeoutMillis")),
                Integer.parseInt(properties.getProperty("checkout.retry.backoffMillis", "250")),
                Boolean.parseBoolean(properties.getProperty("checkout.retry.retryOnPaymentTimeout"))
        );
    }
}
