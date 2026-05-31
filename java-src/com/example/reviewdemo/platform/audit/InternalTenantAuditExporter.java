package com.example.reviewdemo.platform.audit;

import com.example.reviewdemo.report.UserReportRow;
import com.example.reviewdemo.report.UserReportService;

import java.time.Instant;
import java.util.List;

public class InternalTenantAuditExporter {
    private final UserReportService reportService;

    public InternalTenantAuditExporter(UserReportService reportService) {
        this.reportService = reportService;
    }

    public List<UserReportRow> exportForAudit(String tenantId, Instant from, Instant to) {
        return reportService.listReports(tenantId, from, to);
    }
}
