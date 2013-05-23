package com.check.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.check.bean.User;
import com.check.service.ReportService;
import com.check.service.UserService;
import com.check.util.JsonUtil;
import com.check.util.PPUtil;
import com.check.util.WFUtil;
import com.check.util.ZWUtil;

@Controller
@ParentPackage("admin")
@Scope("prototype")
@Results({ 
	@Result(name = "zw", location = "/zwCheck.jsp"),
	@Result(name = "list", location = "/WEB-INF/manager/report_list.jsp")
})
public class ZwAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 535912645768166500L;

	private static Logger logger = Logger.getLogger(ZwAction.class);

	@Resource(name = "reportServiceImpl")
	private ReportService reportService;
	
	@Resource(name = "userServiceImpl")
	private UserService userService;

	private String sz;
	private String title;
	private String author;
	private String orderNo1;
	private String rows;// 每页显示的记录数
	private String page;// 当前第几页
	
	public String zw() {
		return "zw";
	}

	public String check() {
//		logger.info("sz:" + sz);
		try {
			// 提交检测，访问第三放检测网站
			String reportid = ZWUtil.check(title,author,sz);
			if(reportid==null){
				return null;//提示检测异常
			}
			//写入lwreport表
			Report report = new Report();
			report.setSign(3);//知网标志
			report.setTitle(title);
			report.setAuthor(author);
			report.setContent(sz);
			report.setCreate_date(new Date());
			report.setUid(orderNo1);
			report.setLink("underchecking");
			report.setRemark("wait");
			report.setPpid(reportid);
			//计算几个最低单位
			double count=Math.ceil(sz.length()/Double.parseDouble(PPUtil.getProp("zw.per")));
			report.setNeed_price(count*Double.parseDouble(PPUtil.getProp("zw.price")));
			reportService.save(report);
			//更新用户的余额信息
			User user=(User)ServletActionContext.getRequest().getSession().getAttribute("user");
			user.setPrice(user.getPrice()-count*Double.parseDouble(PPUtil.getProp("zw.price")));
			userService.update(user);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 正常检测成功后，插入数据保存,保留报告地址位置为空,返回到list页面
		ServletActionContext.getRequest().getSession().setAttribute("loginUserId", orderNo1);
		return "list";
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

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
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
}
