package com.example.reviewdemo.report;

import com.example.reviewdemo.auth.AuthService;
import com.example.reviewdemo.auth.Session;

import java.time.Instant;
import java.util.List;

public class AdminReportController {
    private final AuthService authService;
    private final UserReportService reportService;

    public AdminReportController(AuthService authService, UserReportService reportService) {
        this.authService = authService;
        this.reportService = reportService;
    }

    public List<UserReportRow> exportTenantReport(
            String accessToken,
            String tenantId,
            Instant from,
            Instant to,
            boolean includeDisabledUsers
    ) {
        Session session = authService.requireSession(accessToken);
        if (!session.isAdmin() && !authService.canAccessTenant(accessToken, session.tenantId())) {
            throw new SecurityException("forbidden");
        }

        List<UserReportRow> rows = reportService.listReports(tenantId, from, to);
        if (includeDisabledUsers) {
            return rows;
        }

        return rows.stream()
                .filter(row -> !"DISABLED".equals(row.status()))
                .toList();
    }
}
