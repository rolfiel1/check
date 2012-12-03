package com.check.filter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 * 
 * @author 赵晓飞
 * 
 * @version 1.0 创建时间：2012-1-6 上午10:00:29
 * 
 *          log日志初始化 - 系统
 */
public class Log4jInit extends HttpServlet {
	private static final long serialVersionUID = 6717383513234981547L;
	private static Logger logger = Logger.getLogger(Log4jInit.class);
	public void init() {
		String prefix = getServletContext().getRealPath("/");
		String file = getInitParameter("log4j");// 配置文件位置
		String filePath = prefix + file;
		Properties props = new Properties();
		try {
			FileInputStream istream = new FileInputStream(filePath);
			props.load(istream);
			istream.close();
			String logFile = prefix + props.getProperty("log4j.appender.R.File");// 设置路径
			props.setProperty("log4j.appender.R.File", logFile);
			PropertyConfigurator.configure(props);// 装入log4j配置信息
		} catch (IOException e) {
			logger.info("Could not read configuration file [" + filePath + "].");
			logger.info("Ignoring configuration file [" + filePath + "].");
			return;
		}

	}
	public void doGet(HttpServletRequest req, HttpServletResponse res)

	{
	}
}