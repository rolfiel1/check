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

import com.check.bean.User;
import com.check.service.UserService;
import com.check.util.JsonUtil;
import com.check.util.MD5Util;

@Controller
@ParentPackage("admin")
@Scope("prototype")
@Results({ 
	@Result(name = "main", location = "/WEB-INF/manager/main.jsp"),
	@Result(name = "changePwd", location = "/WEB-INF/manager/changePwd.jsp"),
@Result(name = "clientList", location = "/WEB-INF/manager/clientList.jsp")})
public class UserAction extends BaseAction {
	private static final long serialVersionUID = 2555487321597652641L;
	private static Logger logger = Logger.getLogger(UserAction.class);
	private String orderNo1;
	private String orderNo2;
	private String validCode;
	private String username;
	private String password;
	private String oldPwd;
	private String newPwd;
	private String rows;// 每页显示的记录数
	private String page;// 当前第几页

	@Resource(name = "userServiceImpl")
	private UserService userService;

	public String logout(){
		ServletActionContext.getRequest().getSession().removeAttribute("user");
		return "tologin";
	}
	public String checkLogin() {
		logger.info("----------login----------");
		logger.info(orderNo1 + "," + orderNo2 + "," + validCode);
		logger.info(ServletActionContext.getRequest().getSession()
				.getAttribute("Svalipicstr"));
		// 先判断订单号，验证码是否正确
		User user = null;
		String sessionValidcode = ServletActionContext.getRequest()
				.getSession().getAttribute("Svalipicstr").toString();
		if (!sessionValidcode.equals(validCode)) {
			return ajax(JsonUtil.toJson("验证码错误,请重新输入验证码!"));
		} else {
			user = userService.checkUser(orderNo1);
			if (user == null) {
				return ajax(JsonUtil.toJson("订单号不存在,请重新输入!"));
			} else {
				// 提交数据到PaperPass，开始检测论文
				return ajax(JsonUtil.toJson("发货成功,正在检测,请稍等..."));
			}

		}
	}

	public String login() {
		logger.info("----------login----------");
		logger.info(orderNo1 + "," + orderNo2 + "," + validCode);
		logger.info(ServletActionContext.getRequest().getSession()
				.getAttribute("Svalipicstr"));
		// 先判断订单号，验证码是否正确
		User user = null;
		String sessionValidcode = ServletActionContext.getRequest()
				.getSession().getAttribute("Svalipicstr").toString();

		if (!sessionValidcode.equals(validCode)) {
			return ajax(JsonUtil.toJson("验证码错误,请重新输入验证码!"));
		} else {
			user = userService.checkUser(orderNo1);
			if (user == null) {
				return ajax(JsonUtil.toJson("订单号不存在,请重新输入!"));
			} else {
				ServletActionContext.getRequest().getSession()
						.setAttribute("user", user);
				return ajax(JsonUtil.toJson("success"));
			}
		}
	}

	/**
	 * 暂无password MD5加密
	 * 
	 * @return
	 */
	public String adminLogin() {
		logger.info("----------adminLogin----------");
		logger.info(username + "," + password + "," + validCode);
		logger.info(ServletActionContext.getRequest().getSession()
				.getAttribute("Svalipicstr"));
		// 先判断订单号，验证码是否正确
		User user = null;
		String sessionValidcode = ServletActionContext.getRequest()
				.getSession().getAttribute("Svalipicstr").toString();

		if (!sessionValidcode.equals(validCode)) {
			return ajax(JsonUtil.toJson("验证码错误,请重新输入验证码!"));
		} else {
			user = userService.adminLogin(username, MD5Util.toMD5(password));
			if (user == null) {
				return ajax(JsonUtil.toJson("用户名或密码错误,请重新输入!"));
			} else {
				ServletActionContext.getRequest().getSession()
						.setAttribute("user", user);
				return ajax(JsonUtil.toJson("success"));
			}
		}
	}
	
	
	public String clientList(){
		return "clientList";
	}
	
	public String ajaxClientList(){
		logger.info("--------ajaxClientList()-----------");
		Map<String, Object> map = new HashMap<String, Object>();
		pager.setPageSize(Integer.parseInt(rows));
		pager.setPageNumber(Integer.parseInt(page));
		pager = userService.findPager(pager, map);
		Map<String, Object> JsonMap = new HashMap<String, Object>();
		JsonMap.put("total", pager.getTotalCount());
		JsonMap.put("rows", pager.getResult());
		return ajax(JsonUtil.toJson(JsonMap));
	}
	
	public String changePwd(){
		return "changePwd";
	}
	
	public String changeP(){
		User user=(User)ServletActionContext.getRequest().getSession().getAttribute("user");
		User entity=userService.adminLogin(user.getUsername(), MD5Util.toMD5(oldPwd));
		Map<String,Object> ret=new HashMap<String,Object>();
		if(entity!=null){
			ret.put("msg", "修改成功,系统将在5秒钟后退出,请重新登录");
			ret.put("ret", "success");
			user.setPassword(MD5Util.toMD5(newPwd));
			userService.update(user);
		}else{
			ret.put("ret", "error");
			ret.put("msg", "原密码输入不正确,请重新输入");
		}
		return ajax(JsonUtil.toJson(ret));
	}

	public String main() {
		return "main";
	}
	
	public String editWZ() {
		return "editWZ";
	}

	public String getOrderNo1() {
		return orderNo1;
	}

	public void setOrderNo1(String orderNo1) {
		this.orderNo1 = orderNo1;
	}

	public String getOrderNo2() {
		return orderNo2;
	}

	public void setOrderNo2(String orderNo2) {
		this.orderNo2 = orderNo2;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	public String getOldPwd() {
		return oldPwd;
	}
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

}
