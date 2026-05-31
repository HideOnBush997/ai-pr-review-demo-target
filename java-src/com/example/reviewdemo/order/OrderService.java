package com.example.reviewdemo.order;

public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryGateway inventoryGateway;
    private final PaymentGateway paymentGateway;
    private final AuditLogger auditLogger;

    public OrderService(
            OrderRepository orderRepository,
            InventoryGateway inventoryGateway,
            PaymentGateway paymentGateway,
            AuditLogger auditLogger
    ) {
        this.orderRepository = orderRepository;
        this.inventoryGateway = inventoryGateway;
        this.paymentGateway = paymentGateway;
        this.auditLogger = auditLogger;
    }

    public Order pay(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("order not found"));
        if (order.status() != OrderStatus.PENDING) {
            throw new IllegalStateException("order is not payable");
        }

        inventoryGateway.reserve(order.sku(), order.quantity());
        String paymentId = paymentGateway.charge(order.id(), order.amount());
        auditLogger.paymentSucceeded(order.id(), paymentId);

        if (order.amount().signum() > 0) {
            Order paid = order.markPaid();
            orderRepository.save(paid);
            return paid;
        }

        auditLogger.paymentFailed(order.id(), "zero amount order");
        return order;
    }
}
