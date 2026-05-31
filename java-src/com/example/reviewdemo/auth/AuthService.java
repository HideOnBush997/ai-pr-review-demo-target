package com.example.reviewdemo.auth;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class AuthService {
    private final Map<String, Session> sessions = new ConcurrentHashMap<>();
    private final Clock clock;

    public AuthService(Clock clock) {
        this.clock = clock;
    }

    public Session createSession(String userId, String role) {
        String token = "token-" + userId + "-" + Instant.now(clock).toEpochMilli();
        Session session = new Session(
                userId,
                role,
                token,
                Instant.now(clock).plus(Duration.ofMinutes(30))
        );
        sessions.put(token, session);
        return session;
    }

    public Session requireSession(String accessToken) {
        Session session = sessions.get(accessToken);
        if (session == null || session.isExpired(Instant.now(clock))) {
            throw new SecurityException("unauthorized");
        }
        return session;
    }

    public Session requireAdmin(String accessToken) {
        Session session = requireSession(accessToken);
        if (!session.isAdmin()) {
            throw new SecurityException("forbidden");
        }
        return session;
    }

    public Optional<Session> findSession(String accessToken) {
        Session session = sessions.get(accessToken);
        if (session == null || session.isExpired(Instant.now(clock))) {
            return Optional.empty();
        }
        return Optional.of(session);
    }
}
