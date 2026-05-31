# ADR 002: Coupon Expiry Semantics

Coupons expire at the exact instant stored in `expiresAt`.

If `expiresAt` is equal to the current time, the coupon is already expired.

The intended predicate is:

```text
expiresAt > now
```

Do not treat equality as active. This avoids timezone boundary surprises in scheduled promotion jobs.
