package com.check.action;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.check.bean.Report;
import com.check.service.ReportService;
import com.check.util.PPUtil;

@Controller
@ParentPackage("admin")
@Scope("prototype")
public class PaperpassAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 535912645768166503L;
	
	private static Logger logger = Logger.getLogger(PaperpassAction.class);
	
	@Resource(name = "reportServiceImpl")
	private ReportService reportService;
	
	private String title;
	private String author;
	private String sz;

	public String ppCheck(){
		logger.info("title:"+title);
		logger.info("author:"+author);
		logger.info("sz:"+sz);
		try {
			PPUtil.check(title, author, sz);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//正常检测成功后，插入数据保存,保留报告地址位置为空
		Report report=new Report();
		report.setSign(1);
		report.setTitle(title);
		report.setAuthor(author);
		report.setContent(sz);
		report.setCreate_date(new Date());
		reportService.save(report);
		
		return null;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSz() {
		return sz;
	}

	public void setSz(String sz) {
		this.sz = sz;
	}
	
}
