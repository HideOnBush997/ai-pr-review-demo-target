package com.example.reviewdemo.report;

import java.util.List;

public interface UserReportStore {
    List<UserReportRow> findByTenant(String tenantId);
}
