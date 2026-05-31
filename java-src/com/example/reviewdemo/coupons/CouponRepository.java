package com.example.reviewdemo.coupons;

import java.util.Optional;

public interface CouponRepository {
    Optional<Coupon> findByCode(String code);
}
