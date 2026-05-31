package com.example.reviewdemo.platform.service;

import com.example.reviewdemo.config.CheckoutRetryPolicy;
import com.example.reviewdemo.order.Order;
import com.example.reviewdemo.order.OrderService;

import java.util.List;

public class BulkCheckoutJob {
    private final OrderService orderService;
    private final CheckoutRetryPolicy retryPolicy;

    public BulkCheckoutJob(OrderService orderService, CheckoutRetryPolicy retryPolicy) {
        this.orderService = orderService;
        this.retryPolicy = retryPolicy;
    }

    public int payAll(List<String> orderIds) {
        int paid = 0;
        for (String orderId : orderIds) {
            RuntimeException lastError = null;
            for (int attempt = 0; attempt < 3; attempt++) {
                try {
                    Order order = orderService.pay(orderId);
                    if ("PAID".equals(order.status().name())) {
                        paid++;
                    }
                    break;
                } catch (RuntimeException error) {
                    lastError = error;
                    if (!retryPolicy.shouldRetry(attempt, error)) {
                        break;
                    }
                }
            }
        }
        return paid;
    }
}
