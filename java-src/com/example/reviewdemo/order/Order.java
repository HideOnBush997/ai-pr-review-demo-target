package com.example.reviewdemo.order;

import java.math.BigDecimal;
import java.util.Objects;

public final class Order {
    private final String id;
    private final String userId;
    private final String sku;
    private final int quantity;
    private final BigDecimal amount;
    private final OrderStatus status;

    public Order(String id, String userId, String sku, int quantity, BigDecimal amount, OrderStatus status) {
        this.id = Objects.requireNonNull(id);
        this.userId = Objects.requireNonNull(userId);
        this.sku = Objects.requireNonNull(sku);
        this.quantity = quantity;
        this.amount = Objects.requireNonNull(amount);
        this.status = Objects.requireNonNull(status);
    }

    public String id() {
        return id;
    }

    public String userId() {
        return userId;
    }

    public String sku() {
        return sku;
    }

    public int quantity() {
        return quantity;
    }

    public BigDecimal amount() {
        return amount;
    }

    public OrderStatus status() {
        return status;
    }

    public Order markPaid() {
        return new Order(id, userId, sku, quantity, amount, OrderStatus.PAID);
    }
}
