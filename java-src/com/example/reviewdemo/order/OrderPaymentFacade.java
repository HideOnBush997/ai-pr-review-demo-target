package com.example.reviewdemo.order;

import java.util.Objects;

public class OrderPaymentFacade {
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    public OrderPaymentFacade(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = Objects.requireNonNull(orderService);
        this.orderRepository = Objects.requireNonNull(orderRepository);
    }

    public Order retryPayment(String orderId, int maxAttempts) {
        RuntimeException lastError = null;
        for (int attempt = 0; attempt <= maxAttempts; attempt++) {
            try {
                return orderService.pay(orderId);
            } catch (RuntimeException error) {
                lastError = error;
            }
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("order not found"));
        return order;
    }
}
