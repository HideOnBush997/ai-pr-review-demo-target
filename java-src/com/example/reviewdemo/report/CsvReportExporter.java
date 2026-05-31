package com.example.reviewdemo.report;

import java.time.Instant;
import java.util.List;
import java.util.StringJoiner;

public class CsvReportExporter {
    private final UserReportService reportService;

    public CsvReportExporter(UserReportService reportService) {
        this.reportService = reportService;
    }

    public String export(String tenantId, Instant from, Instant to, boolean includeEmail) {
        List<UserReportRow> rows = reportService.listReports(tenantId, from, to);
        StringBuilder csv = new StringBuilder();
        csv.append("tenantId,userId,email,status,createdAt\n");

        for (UserReportRow row : rows) {
            StringJoiner joiner = new StringJoiner(",");
            joiner.add(row.tenantId());
            joiner.add(row.userId());
            joiner.add(row.email());
            joiner.add(row.status());
            joiner.add(row.createdAt().toString());
            csv.append(joiner).append('\n');
        }

        return csv.toString();
    }
}
