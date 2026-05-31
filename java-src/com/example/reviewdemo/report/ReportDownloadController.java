package com.example.reviewdemo.report;

import java.time.Instant;

public class ReportDownloadController {
    private final CsvReportExporter exporter;

    public ReportDownloadController(CsvReportExporter exporter) {
        this.exporter = exporter;
    }

    public String downloadCsv(String tenantId, String fromIso, String toIso) {
        Instant from = Instant.parse(fromIso);
        Instant to = Instant.parse(toIso);
        return exporter.export(tenantId, from, to, false);
    }
}
