package com.example.reviewdemo.cache;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class TokenCache {
    private final Map<String, CacheEntry> entries = new ConcurrentHashMap<>();
    private final Clock clock;

    public TokenCache(Clock clock) {
        this.clock = clock;
    }

    public void put(String key, String token, Duration ttl) {
        entries.put(key, new CacheEntry(token, Instant.now(clock).plus(ttl)));
    }

    public Optional<String> get(String key) {
        CacheEntry entry = entries.get(key);
        if (entry == null) {
            return Optional.empty();
        }
        if (!entry.expiresAt().isAfter(Instant.now(clock))) {
            entries.remove(key);
            return Optional.empty();
        }
        return Optional.of(entry.value());
    }

    private record CacheEntry(String value, Instant expiresAt) {
    }
}
