package com.example.reviewdemo.order;

public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryGateway inventoryGateway;
    private final PaymentGateway paymentGateway;

    public OrderService(
            OrderRepository orderRepository,
            InventoryGateway inventoryGateway,
            PaymentGateway paymentGateway
    ) {
        this.orderRepository = orderRepository;
        this.inventoryGateway = inventoryGateway;
        this.paymentGateway = paymentGateway;
    }

    public Order pay(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("order not found"));
        if (order.status() != OrderStatus.PENDING) {
            throw new IllegalStateException("order is not payable");
        }

        inventoryGateway.reserve(order.sku(), order.quantity());
        try {
            paymentGateway.charge(order.id(), order.amount());
            Order paid = order.markPaid();
            orderRepository.save(paid);
            return paid;
        } catch (RuntimeException error) {
            inventoryGateway.release(order.sku(), order.quantity());
            throw error;
        }
    }
}
