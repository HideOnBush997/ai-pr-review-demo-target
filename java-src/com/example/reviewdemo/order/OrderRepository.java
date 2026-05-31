package com.example.reviewdemo.order;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class OrderRepository {
    private final Map<String, Order> orders = new ConcurrentHashMap<>();

    public Optional<Order> findById(String id) {
        return Optional.ofNullable(orders.get(id));
    }

    public void save(Order order) {
        orders.put(order.id(), order);
    }
}
