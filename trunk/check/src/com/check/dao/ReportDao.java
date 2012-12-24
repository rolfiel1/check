package com.check.dao;

import com.check.bean.Report;

public interface ReportDao extends BaseMybatisDao<Report, String>{

	public Report checkReport(String ppid);
}
