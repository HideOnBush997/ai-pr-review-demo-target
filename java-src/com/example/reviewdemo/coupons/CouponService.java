package com.example.reviewdemo.coupons;

import java.time.Clock;
import java.time.Instant;

public class CouponService {
    private final CouponRepository repository;
    private final Clock clock;

    public CouponService(CouponRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }

    public boolean isCouponActive(String code) {
        return repository.findByCode(code)
                .map(coupon -> !coupon.expiresAt().isBefore(Instant.now(clock)))
                .orElse(false);
    }
}
