package com.check.util;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.httpclient.HttpException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.check.util.top.Proposal;

public class PPDownloadReportUtil extends TimerTask {
//	private static Date initDate=null;
	@Override
	public void run() {
		try {
//			if(initDate==null){
//				initDate=new Date();
//				//启动订单初始化程序只允许一次
//				Proposal.startProposal3();
//			}
			// 下载报告
			List<String> ret = PPUtil.viewReport();
			if (ret != null && ret.size() > 0) {
				// 如果有数据，说明有下载的报告
				// 得到下载报告的名称后，从数据库中查询出相应的ppid与名称相符，并且link为空的
				for (String str : ret) {
					String sql = "select count(0) from lwreport t  where t.link='underchecking' and t.ppid='"
							+ str + "'";
					int flag = ((JdbcTemplate) SpringUtil
							.getBean("jdbcTemplate")).queryForInt(sql);
					if (flag > 0) {
						String update_sql = "update lwreport set link='" + str
								+ "' where ppid='" + str + "'";
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
