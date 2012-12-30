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

@Controller
@ParentPackage("admin")
@Scope("prototype")
@Results({ 
	@Result(name = "wf", location = "/wfCheck.jsp"),
	@Result(name = "list", location = "/WEB-INF/manager/report_list.jsp")
})
public class WanfangAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 535912645768166503L;

	private static Logger logger = Logger.getLogger(WanfangAction.class);

	@Resource(name = "reportServiceImpl")
	private ReportService reportService;
	
	@Resource(name = "userServiceImpl")
	private UserService userService;

	private String sz;
	private String orderNo1;
	private String rows;// 每页显示的记录数
	private String page;// 当前第几页
	
	public String wf() {
		return "wf";
	}

	public String check() {
		logger.info("sz:" + sz);
		try {
			// 提交检测
			String ret = WFUtil.check(sz);
			Report report = new Report();
			report.setSign(1);
			report.setContent(sz);
			report.setCreate_date(new Date());
			report.setUid(orderNo1);
			report.setLink("underchecking");
			// 取第一个
			report.setWfid(ret);
			//计算几个最低单位
			double count=Math.ceil(sz.length()/Double.parseDouble(PPUtil.getProp("wf.per")));
			report.setNeed_price(count*Double.parseDouble(PPUtil.getProp("wf.price")));
			reportService.save(report);
			//更新用户的余额信息
			User user=(User)ServletActionContext.getRequest().getSession().getAttribute("user");
			user.setPrice(user.getPrice()-count*Double.parseDouble(PPUtil.getProp("wf.price")));
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

}
