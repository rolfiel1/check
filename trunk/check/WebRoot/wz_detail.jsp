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
<div class="lwjc_top">
  <div class="gnl">
    <ul>
      <li><a href="#"><span class="tb_4"></span>ENGLISH</a></li>
      <li><a href="#"><span class="tb_3"></span>联系我们</a></li>
      <li><a href="#"><span class="tb_2"></span>加入我们</a></li>
      <li><a href="#"><span class="tb_1"></span>登录|注册</a></li>
    </ul>
  </div>
  <ul class="lwjc_dh">
    <li><a href="#">首页</a></li>
    <li><a href="#">网络检测</a></li>
    <li><a href="#">论文检测</a></li>
    <li><a href="#">期刊检测</a></li>
    <li><a href="#">收费充值</a></li>
    <li><a href="#">文档转换</a></li>
    <li><a href="#">论文修改</a></li>
    <li><a href="#">关于我们</a></li>
  </ul>
</div>
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



<div class="lwjc_bottom">
  <div class="top_lb">
    <div class="top_cjwt">
      <ul>
        <li class="cjwt_bt"><span class="bottom_bt">常见问题</span><span class="bottom_tb"></span></li>
        <li><a href="#">常见问题常见问题常见问题常见问题</a></li>
        <li><a href="#">常见问题常见问题常见问题常见问题</a></li>
        <li><a href="#">常见问题常见问题常见问题常见问题</a></li>
        <li><a href="#">常见问题常见问题常见问题常见问题</a></li>
      </ul>
    </div>
    <div class="top_gycx">
      <ul>
        <li class="cjwt_bt"><span class="bottom_bt">常见问题</span><span class="bottom_tb"></span></li>
        <li><a href="#">常见问题常见问题常见问题常见问题</a></li>
        <li><a href="#">常见问题常见问题常见问题常见问题</a></li>
        <li><a href="#">常见问题常见问题常见问题常见问题</a></li>
        <li><a href="#">常见问题常见问题常见问题常见问题</a></li>
      </ul>
    </div>
    <div class="top_gylc">
      <ul>
        <li class="cjwt_bt"><span class="bottom_bt">常见问题</span><span class="bottom_tb"></span></li>
        <li><a href="#">常见问题常见问题常见问题常见问题</a></li>
        <li><a href="#">常见问题常见问题常见问题常见问题</a></li>
        <li><a href="#">常见问题常见问题常见问题常见问题</a></li>
        <li><a href="#">常见问题常见问题常见问题常见问题</a></li>
      </ul>
    </div>
    <div class="top_sfsm">
      <ul>
        <li class="cjwt_bt"><span class="bottom_bt">常见问题</span><span class="bottom_tb"></span></li>
        <li><a href="#">常见问题常见问题常见问题常见问题</a></li>
        <li><a href="#">常见问题常见问题常见问题常见问题</a></li>
        <li><a href="#">常见问题常见问题常见问题常见问题</a></li>
        <li><a href="#">常见问题常见问题常见问题常见问题</a></li>
      </ul>
    </div>
  </div>
  <div class="lwjc_bqxx">
    <ul>
      <li> 关于网站 | 检测帮助 | 免责声明 | 合作伙伴 | 友情连接 | 加入我们 | 联系方式</li>
      <li>Copyright 2010 zhe.org.All Rights Reserved</li>
      <li>本站联系地址：湖南益阳市河东区解放路28号 本站联系电话：4006083200 </li>
      <li>版权所有：友情链接: hi7网 中国论文检测网 华农电子商务协会 衡阳师范学院论坛 湖北第二师范学院自考 拷克论文检测 论文发表</li>
    </ul>
  </div>
</div>
</div>
</body>
</html>
