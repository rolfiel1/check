package com.check.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.check.service.ReportService;
import com.check.util.JsonUtil;

@Controller
@ParentPackage("admin")
@Scope("prototype")
@Results({ 
	@Result(name = "list", location = "/WEB-INF/manager/report_list.jsp")
})
public class ReportAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 535912645768166503L;

	private static Logger logger = Logger.getLogger(ReportAction.class);

	@Resource(name = "reportServiceImpl")
	private ReportService reportService;

	private String title;
	private String author;
	private String sz;
	private String orderNo1;
	private String rows;// 每页显示的记录数
	private String page;// 当前第几页
	
	public String list() {
		return "list";
	}
	public String ajaxList() {
		logger.info("--------ajaxList-----------");
		logger.info("loginUserId:" + id);
		Map<String, Object> map = new HashMap<String, Object>();
		pager.setPageSize(Integer.parseInt(rows));
		pager.setPageNumber(Integer.parseInt(page));
		map.put("uid", id);
		pager = reportService.findPager(pager, map);
		Map<String, Object> JsonMap = new HashMap<String, Object>();
		JsonMap.put("total", pager.getTotalCount());
		JsonMap.put("rows", pager.getResult());
		return ajax(JsonUtil.toJson(JsonMap));
	}

	public String delete() {
		logger.info("--------delete-----------");
		logger.info("-----ids----" + ids);
		Map<String, Object> JsonMap = new HashMap<String, Object>();
		try {
			reportService.delete(ids);
			JsonMap.put("msg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			JsonMap.put("msg", "删除异常,请稍后再试");
		}
		return ajax(JsonUtil.toJson(JsonMap));
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
