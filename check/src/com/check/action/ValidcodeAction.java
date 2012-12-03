package com.check.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.check.util.GenerateValidatePic;

@Controller
@ParentPackage("admin")
@Scope("prototype")
//@Results({ @Result(name = "success", location = "/ppCheck.jsp") })
public class ValidcodeAction extends BaseAction {
	private static final long serialVersionUID = 2555487321597652661L;
	
	public String getValidcode(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpSession session = request.getSession();
        GenerateValidatePic gPic = new GenerateValidatePic(response);
		String valipicstr = gPic.generateRandPic();
		session.setAttribute("Svalipicstr", valipicstr);
		return null;
	}

}
