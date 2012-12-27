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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="css/index.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div class="box">
<%@ include file="top.jsp"%>
<div class="lwjc_gg"></div>
<div class="lwjc_wzy">
<div class="wzy_left">
<h1>${detail.title }</h1> 
<div class="wzy_xxly"><span>新闻来源：${detail.author }</span></div>
${detail.content }
</div>
<div class="wzy_right">
<div class="wzy_fwxx">
<div class="fwxx_bt">服务信息<span>检测六次，尊享八折优惠！！</span></div>
<ul class="fwxx_info_right">
<li><a href="#">快易好省，车险由你掌握腾讯</a></li>
<li><a href="#">快易好省，车险由你掌握腾讯</a></li>
<li><a href="#">快易好省，车险由你掌握腾讯</a></li>
<li><a href="#">快易好省，车险由你掌握腾讯</a></li>
<li><a href="#">快易好省，车险由你掌握腾讯</a></li>
<li><a href="#">快易好省，车险由你掌握腾讯</a></li>
</ul>

<ul class="fwxx_info">
<li><a href="#">快易好省，车险由你掌握腾讯</a></li>
<li><a href="#">快易好省，车险由你掌握腾讯</a></li>
<li><a href="#">快易好省，车险由你掌握腾讯</a></li>
<li><a href="#">快易好省，车险由你掌握腾讯</a></li>
<li><a href="#">快易好省，车险由你掌握腾讯</a></li>
<li><a href="#">快易好省，车险由你掌握腾讯</a></li>
</ul>

</div>
</div>
</div>
<%@ include file="bottom.jsp"%>
</div>
</body>
</html>
