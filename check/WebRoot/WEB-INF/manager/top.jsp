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
<link href="<%=basePath%>css/index.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="login_top">
  <div class="center_bj">
    <div class="xtdh">
      <ul>
        <li><a href="#">首页</a></li>
        <li><a href="#">知网系统</a></li>
        <li><a href="#">万方系统</a></li>
        <li><a href="#">paperpass系统</a></li>
      </ul>
    </div>
  </div>
</div>
</body>
</html>
