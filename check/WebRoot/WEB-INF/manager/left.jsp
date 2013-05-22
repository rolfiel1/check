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
<script type="text/javascript" src="<%=basePath%>js/sdmenu.js">
	/***********************************************
	 * Slashdot Menu script- By DimX
	 * Submitted to Dynamic Drive DHTML code library: http://www.dynamicdrive.com
	 * Visit Dynamic Drive at http://www.dynamicdrive.com/ for full source code
	 ***********************************************/
</script>
<script type="text/javascript">
	//          
	var myMenu;
	window.onload = function() {
		myMenu = new SDMenu("my_menu");
		myMenu.init();
	};
	//
</script>
</head>
<body style="background:#F0EFE6;">
	<div class="login_info">
		当前登录：<span style="margin:0px 8px;">${user.username }</span><a href="user!logout.action"
			target="_top">退出登录</a>
	</div>
	<div style="height:400px;overflow:auto;">
		<div id="my_menu" class="sdmenu">
			<div>
				<span>用户管理 </span> 
				<a href="user!clientList.action" target="rightFrame">用户列表</a> 
				<a href="user!changePwd.action" target="rightFrame">修改密码</a>
			</div>
			<div>
				<span>文章管理</span> 
				<a href="article!list.action" target="rightFrame">文章列表</a>
				<a href="article!edit.action" target="rightFrame">文章录入</a>
			</div>
		</div>
	</div>
</body>
</html>
