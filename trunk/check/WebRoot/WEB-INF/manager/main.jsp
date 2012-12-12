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
<link href="<%=basePath%>/css/index.css" rel="stylesheet" type="text/css" />
</head>
<body style="background:none;">
	<div class="login_top">
		<div class="center_bj">
			<div class="xtdh">
				<ul>
					<li><a href="#">首页</a>
					</li>
					<li><a href="#">知网系统</a>
					</li>
					<li><a href="#">万方系统</a>
					</li>
					<li><a href="#">paperpass系统</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="login_center">
		<div class="center_bj1">
			<div class="left_info">
				<div class="tb_box">
					<table width="100%" border="0">
						<tr>
							<td width="16"><span class="user_tb"></span>
							</td>
							<th align="right" width="70">当前用户：</th>
							<td>${user.username }</td>
						</tr>
						<tr>
							<td><span class="yhxt_tb"></span></td>
							<td></td>
							<td><a href="" target="mainFrame">用户列表</a>
							</td>
						</tr>
						<tr>
							<td><span class="yhxt_tb"></span></td>
							<td></td>
							<td><a href="" target="mainFrame">文章管理</a>
							</td>
						</tr>
						<tr>
							<td><span class="yhxt_tb"></span></td>
							<td></td>
							<td><a href="" target="mainFrame">修改密码</a>
							</td>
						</tr>
						<tr>
							<td><span class="yhxt_tb"></span></td>
							<td></td>
							<td><a href="">退出系统</a>
							</td>
						</tr>
					</table>
				</div>

			</div>
			<div class="right_info">
				<iframe id="mainFrame" name="mainFrame"   width="100%" height="100%"></iframe>
			</div>
		</div>
	</div>
	<div class="login_bottom">
		<div class="bottom_bj">
			主办单位：论文检测办公室 版权所有：zlking工作室<br>技术支持：zlking工作室 技术热线：028-67167178
				ICP备案号：苏ICP备12070094号 
		</div>
	</div>
</body>
</html>