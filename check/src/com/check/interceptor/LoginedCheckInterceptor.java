package com.check.interceptor;

import org.apache.struts2.ServletActionContext;

import com.check.bean.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/** session过期、登录有效性及操作的权限验证拦截器 */
@SuppressWarnings("serial")
public class LoginedCheckInterceptor extends AbstractInterceptor {

	/** 拦截请求并进行登录有效性验证 */
	public String intercept(ActionInvocation ai) throws Exception {
		// 取得请求的URL
		String url = ServletActionContext.getRequest().getRequestURL()
				.toString();
		User user = null;
		if (url.indexOf("user!ppCheck.action") != -1
				|| url.indexOf("validcode!getValidcode.action") != -1
				|| url.indexOf("user!checkLogin.action") != -1
				|| url.indexOf("user!login.action") != -1) {
			return ai.invoke();
		} else {
			// 验证Session是否过期
			if (!ServletActionContext.getRequest().isRequestedSessionIdValid()) {
				// session过期,转向session过期提示页,最终跳转至登录页面
				return "tologin";
			} else {
				user = (User) ServletActionContext.getRequest().getSession()
						.getAttribute("user");
				// 验证是否已经登录
				if (user == null) {
					// 尚未登录,跳转至登录页面
					return "tologin";
				} else {
					return ai.invoke();
				}
			}
		}
	}
}