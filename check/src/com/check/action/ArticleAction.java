package com.check.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.check.bean.Article;
import com.check.bean.Pager;
import com.check.service.ArticleService;
import com.check.util.JsonUtil;

@Controller
@ParentPackage("admin")
@Scope("prototype")
@Results({ @Result(name = "list", location = "/WEB-INF/manager/wz_list.jsp"),
		@Result(name = "detail", location = "/wz_detail.jsp"),
		@Result(name = "edit", location = "/WEB-INF/manager/wz_input.jsp") })
public class ArticleAction extends BaseAction {
	private static final long serialVersionUID = 2555487343597652641L;
	private static Logger logger = Logger.getLogger(ArticleAction.class);
	private Article article;
	private String rows;// 每页显示的记录数
	private String page;// 当前第几页

	private String type;

	private String ajaxId;
	@Resource(name = "articleServiceImpl")
	private ArticleService articleService;

	public String save() {
		logger.info("----文章保存-----");
		articleService.save(article);
		return "list";
	}

	public String list() {

		return "list";
	}

	public String edit() {
		article = articleService.load(ajaxId);
		ServletActionContext.getRequest().setAttribute("article", article);
		return "edit";
	}

	public String delete() {
		logger.info("--------delete-----------");
		logger.info("-----ids----" + ids);
		Map<String, Object> JsonMap = new HashMap<String, Object>();
		try {
			articleService.delete(ids);
			JsonMap.put("msg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			JsonMap.put("msg", "删除异常,请稍后再试");
		}
		return ajax(JsonUtil.toJson(JsonMap));
	}

	public String ajaxList() {
		logger.info("----article----ajaxList()-----------");
		Map<String, Object> map = new HashMap<String, Object>();
		pager.setPageSize(Integer.parseInt(rows));
		pager.setPageNumber(Integer.parseInt(page));
		pager = articleService.findPager(pager, map);
		Map<String, Object> JsonMap = new HashMap<String, Object>();
		JsonMap.put("total", pager.getTotalCount());
		JsonMap.put("rows", pager.getResult());
		return ajax(JsonUtil.toJson(JsonMap));
	}

	public String detail() {
		Article detail=articleService.load(id);
		ServletActionContext.getRequest().setAttribute("detail", detail);
		return "detail";
	}

	public String show4() {
		logger.info("----article----show4()-----------");
		Map<String, Object> map = new HashMap<String, Object>();
		pager.setPageSize(4);
		pager.setPageNumber(1);
		map.put("type", type);
		pager = articleService.findPager(pager, map);
		System.out.println(JsonUtil.toJson(pager.getResult()));
		return ajax(JsonUtil.toJson(pager.getResult()));
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
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

	public String getAjaxId() {
		return ajaxId;
	}

	public void setAjaxId(String ajaxId) {
		this.ajaxId = ajaxId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
