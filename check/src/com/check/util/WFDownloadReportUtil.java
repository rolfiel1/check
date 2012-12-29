package com.check.util;

import java.io.IOException;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.httpclient.HttpException;
import org.springframework.jdbc.core.JdbcTemplate;
public class WFDownloadReportUtil extends TimerTask {
	@Override
	public void run() {
		try {
			// 下载报告
			List<String> ret = WFUtil.viewReport();
			if (ret != null && ret.size() > 0) {
				// 如果有数据，说明有下载的报告
				// 得到下载报告的名称后，从数据库中查询出相应的wfid与名称相符，并且link为空的
				for (String str : ret) {
					String sql = "select count(0) from lwreport t  where t.link='underchecking' and t.wfid='"
							+ str + "'";
					int flag = ((JdbcTemplate) SpringUtil
							.getBean("jdbcTemplate")).queryForInt(sql);
					if (flag > 0) {
						String update_sql = "update lwreport set link='" + str
								+ "' where wfid='" + str + "'";
						((JdbcTemplate) SpringUtil.getBean("jdbcTemplate"))
								.update(update_sql);
					}
				}
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
