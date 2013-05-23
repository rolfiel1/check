package com.check.util;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.httpclient.HttpException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.check.bean.Report;

public class ZWDownloadReportUtil extends TimerTask {
	@Override
	public void run() {
		try {
			
			
			List<Report> list = ((JdbcTemplate) SpringUtil
					.getBean("jdbcTemplate")).query(
			        "select  t.ppid from lwreport t where t.link='underchecking' and t.sign = '3' and t.remark  ='wait' ",
			        new RowMapper<Report>() {
			            public Report mapRow(ResultSet rs, int rowNum) throws SQLException {
			            	Report re = new Report();
			            	re.setPpid(rs.getString("ppid"));
			                return re;
			            }
			        });
			
			// 从lwreport表查询未检测的报告的ppid
//			String sql = "select  * from lwreport t where t.link='underchecking' and t.sign = '3' and t.remark  ='wait' ";
//			List<Report> list = ((JdbcTemplate) SpringUtil
//					.getBean("jdbcTemplate")).queryForList(sql, Report.class);

			if (list.isEmpty()) {
				return;
			}
//			List<Report> list = new ArrayList<Report>();
//			Report report = new Report();
//			report.setId(3);
//			report.setPpid("90028482");
//			list.add(report);
			List<String> ret = ZWUtil.viewReport(list);
			if (ret.isEmpty()) {
				return;
			}
			System.out.println("ret:" + ret);
			// 报告网页路径,更新lwreport
			for (String strs : ret) {
				String str[] = strs.split("#");
				System.out.println("reportFilePath:" + str[1]);
				String update_sql = "update lwreport set link='" + str[1]
						+ "' where ppid='" + str[0] + "'";
				((JdbcTemplate) SpringUtil.getBean("jdbcTemplate"))
						.update(update_sql);
			}

		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
