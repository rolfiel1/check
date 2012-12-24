package com.check.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.check.bean.Report;
import com.check.dao.BaseMybatisDao;
import com.check.dao.ReportDao;
import com.check.service.ReportService;

@Service("reportServiceImpl")
public class ReportServiceImpl extends BaseMybatisServiceImpl<Report, String> implements ReportService{
	@Resource(name = "reportDaoImpl")
	public void setBaseDao(BaseMybatisDao<Report, String> reportDao) {
		super.setBaseMybatisDao(reportDao);
	}
	@Resource(name = "reportDaoImpl")
	private ReportDao reportDao;
	@Override
	public Report checkReport(String ppid) {
		return reportDao.checkReport(ppid);
	}

}
