package com.example.reviewdemo.report;

import java.time.Instant;
import java.util.List;

public class InternalReportJob {
    private final UserReportService reportService;

    public InternalReportJob(UserReportService reportService) {
        this.reportService = reportService;
    }

    public List<UserReportRow> runNightlyTenantExport(String tenantId, Instant from, Instant to) {
        return reportService.listReports(tenantId, from, to);
    }
}
