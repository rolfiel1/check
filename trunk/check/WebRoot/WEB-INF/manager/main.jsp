<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>百分百检测系统--管理员主页面</title>
</head>
<frameset rows="75,*,75" frameborder="no" border="0" framespacing="0">
  <frame src="user!top.action" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />
  <frameset cols="260,*" frameborder="0" border="0" framespacing="0" bordercolor="#5D7E83">
    <frame src="user!left.action" name="leftFrame" frameborder="0" border="0" scrolling="no"  id="leftFrame" title="leftFrame" />
    <frame src="" name="rightFrame" frameborder="0" border="0" scrolling="auto"  id="rightFrame" title="rightFrame" />
  </frameset>
  <frame src="user!bottom.action" name="bottomFrame" scrolling="No" noresize="noresize" id="bottomFrame" title="bottomFrame" />
</frameset>
    <noframes>此页面为框架页面，如果您看到此页面，证明您的浏览器不能有效显示本页面，请更换为IE或火狐浏览器后再访问本页面。</noframes>
</html>
