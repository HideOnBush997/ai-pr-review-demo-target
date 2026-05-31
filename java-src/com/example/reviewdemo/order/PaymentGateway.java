package com.example.reviewdemo.order;

import java.math.BigDecimal;

public interface PaymentGateway {
    String charge(String orderId, BigDecimal amount);
}
