package com.check.service;

import com.check.bean.Report;

public interface ReportService extends BaseMybatisService<Report, String> {
	public Report checkReport(String ppid);
}
