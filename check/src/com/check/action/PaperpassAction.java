package com.check.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.check.bean.Report;
import com.check.service.ReportService;
import com.check.util.PPUtil;

@Controller
@ParentPackage("admin")
@Scope("prototype")
@Results({ @Result(name = "list", location = "/WEB-INF/manager/pp_list.jsp") })
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
	private String orderNo1;

	public String ppCheck(){
		logger.info("title:"+title);
		logger.info("author:"+author);
		logger.info("sz:"+sz);
		try {
			//提交检测
			List<String >ret= PPUtil.check(title, author, sz);
			Report report=new Report();
			report.setSign(1);
			report.setTitle(title);
			report.setAuthor(author);
			report.setContent(sz);
			report.setCreate_date(new Date());
			report.setUid(orderNo1);
			//取第一个
			report.setPpid(ret.get(0));
			reportService.save(report);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//正常检测成功后，插入数据保存,保留报告地址位置为空,返回到list页面
		ServletActionContext.getRequest().getSession().setAttribute("loginUserId", orderNo1);
		return "list";
	}
	
	public String ajaxList(){
		logger.info("--------ajaxList-----------");
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

	public String getOrderNo1() {
		return orderNo1;
	}

	public void setOrderNo1(String orderNo1) {
		this.orderNo1 = orderNo1;
	}
	
}
