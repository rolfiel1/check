package com.check.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.check.bean.Report;
import com.check.dao.ReportDao;

@Repository("reportDaoImpl")
public class ReportDaoImpl extends BaseMybatisDaoImpl<Report, String> implements ReportDao {
	private static Logger logger = Logger.getLogger(ReportDaoImpl.class);

}
