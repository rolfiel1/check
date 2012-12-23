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
	<div class="login_bottom">
		<div class="bottom_bj">
			主办单位：论文检测办公室 版权所有：zlking工作室<br>技术支持：zlking工作室 技术热线：028-67167178
				ICP备案号：苏ICP备12070094号
		</div>
	</div>
</body>
</html>
