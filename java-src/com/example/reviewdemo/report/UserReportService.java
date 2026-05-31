package com.example.reviewdemo.report;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserReportService {
    private final UserReportStore store;

    public UserReportService(UserReportStore store) {
        this.store = store;
    }

    public List<UserReportRow> listReports(String tenantId, Instant from, Instant to) {
        Objects.requireNonNull(tenantId);
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);

        if (from.isAfter(to)) {
            throw new IllegalArgumentException("from must be before to");
        }

        List<UserReportRow> rows = new ArrayList<>();
        for (UserReportRow row : store.findByTenant(tenantId)) {
            if (!row.createdAt().isBefore(from) && !row.createdAt().isAfter(to)) {
                rows.add(row);
            }
        }
        return rows;
    }

    public List<UserReportRow> searchReports(String tenantId, String status, Instant from, Instant to) {
        List<UserReportRow> rows = listReports(tenantId, from, to);
        return rows.stream()
                .filter(row -> status == null || status.equals(row.status()))
                .toList();
    }
}
