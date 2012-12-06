package com.check.action;

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
@Controller
@ParentPackage("admin")
@Scope("prototype")
@Results({ @Result(name = "ppCheck", location = "/ppCheck.jsp") })
public class UserAction extends BaseAction {
	private static final long serialVersionUID = 2555487321597652641L;
	private static Logger logger = Logger.getLogger(UserAction.class);
	private String orderNo1;
	private String orderNo2;
	private String validCode;
	
	@Resource(name = "userServiceImpl")
	private UserService userService;
	
	public String ppCheck(){
		return "ppCheck";
	}
	public String checkLogin(){
		logger.info("----------login----------");
		logger.info(orderNo1+","+orderNo2+","+validCode);
		logger.info(ServletActionContext.getRequest().getSession().getAttribute("Svalipicstr"));
		//先判断订单号，验证码是否正确
		User user=null;
		String sessionValidcode=ServletActionContext.getRequest().getSession().getAttribute("Svalipicstr").toString();
		if(!sessionValidcode.equals(validCode)){
			return ajax(JsonUtil.toJson("验证码错误,请重新输入验证码!"));
		}else{
			user=userService.checkUser(orderNo1);
			if(user==null){
				return ajax(JsonUtil.toJson("订单号不存在,请重新输入!"));
			}else{
				//提交数据到PaperPass，开始检测论文
				return ajax(JsonUtil.toJson("发货成功,正在检测,请稍等..."));
			}
			
		}
	}
	
	public String login(){
		logger.info("----------login----------");
		logger.info(orderNo1+","+orderNo2+","+validCode);
		logger.info(ServletActionContext.getRequest().getSession().getAttribute("Svalipicstr"));
		//先判断订单号，验证码是否正确
		User user=null;
		String sessionValidcode=ServletActionContext.getRequest().getSession().getAttribute("Svalipicstr").toString();
		
		if(!sessionValidcode.equals(validCode)){
			return ajax(JsonUtil.toJson("验证码错误,请重新输入验证码!"));
		}else{
			user=userService.checkUser(orderNo1);
			if(user==null){
				return ajax(JsonUtil.toJson("订单号不存在,请重新输入!"));
			}else{
				ServletActionContext.getRequest().getSession().setAttribute("user", user);
				return ajax(JsonUtil.toJson("success"));
			}
		}
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

	
}
