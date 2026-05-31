package com.example.reviewdemo.coupons;

import java.time.Instant;

public record Coupon(
        String code,
        int percentOff,
        Instant expiresAt,
        boolean firstPurchaseOnly
) {
}
