package com.example.reviewdemo.order;

import com.example.reviewdemo.config.CheckoutRetryPolicy;

public class CheckoutRetryRunner {
    private final OrderService orderService;
    private final CheckoutRetryPolicy retryPolicy;

    public CheckoutRetryRunner(OrderService orderService, CheckoutRetryPolicy retryPolicy) {
        this.orderService = orderService;
        this.retryPolicy = retryPolicy;
    }

    public Order payWithRetry(String orderId) {
        RuntimeException lastError = null;
        for (int attempt = 0; attempt < 3; attempt++) {
            try {
                return orderService.pay(orderId);
            } catch (RuntimeException error) {
                lastError = error;
                if (!retryPolicy.shouldRetry(attempt, error)) {
                    throw error;
                }
            }
        }
        throw lastError;
    }
}
