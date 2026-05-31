package com.example.reviewdemo.tests.java;

import com.example.reviewdemo.coupons.Coupon;
import com.example.reviewdemo.coupons.CouponRepository;
import com.example.reviewdemo.coupons.CouponService;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Optional;

public class CouponServiceTest {
    public boolean expiredCouponIsInactive() {
        CouponRepository repository = code -> Optional.of(
                new Coupon(code, 10, Instant.parse("2026-01-01T00:00:00Z"), false)
        );
        CouponService service = new CouponService(
                repository,
                Clock.fixed(Instant.parse("2026-02-01T00:00:00Z"), ZoneOffset.UTC)
        );
        return !service.isCouponActive("WELCOME10");
    }
}
