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
<title>百分百论文检测系统--管理员登录界面</title>
<link href="<%=basePath%>css/index.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="<%=basePath%>/js/jquery-1.8.1.min.js"></script>
<script language="javascript" src="<%=basePath%>/js/motel.js"></script>
</head>

<body style="background:none;">
	<script type="text/javascript">

		// 登录页面若在框架内，则跳出框架
		if (self != top) {
			top.location = self.location;
		};

	</script>
<div class="login_top">
  <div class="center_bj">
    <div class="xtdh">
      <ul>
        <li><a href="<%=basePath%>/index.html">首页</a></li>
        <li><a href="#">知网系统</a></li>
        <li><a href="#">万方系统</a></li>
        <li><a href="user!ppCheck.action">paperpass系统</a></li>
      </ul>
    </div>
  </div>
</div>
<div class="login_center">

  <div class="center_bj">
  <div style="float:right; width:272px; margin:0px 50px 0px auto; display:inline;">
    <div class="login">
      <div class="login_tb">
        <table  border="0" width="100%">
          <tr>
            <td><div class="tb"></div></td>
            <td colspan="2" class="yhdl">用户登录</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td class="th">用户名：</td>
            <td><input type="text" name="username" id="username"  class="wbk"/>
              <span style="color:#f00">*</span></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td class="th">密码：</td>
            <td><input type="password" name="password" id="password"  class="wbk"/>
              <span style="color:#f00">*</span></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td class="th">验证码：</td>
            <td>
            	<input type="text" name="validCode" id="validCode" class="ddh"/>
            	<img src='validcode!getValidcode.action?date=<%=new Date() %>'  onclick="changeValidcode()" alt='点击刷新验证码' align='' id='random' valign='absmiddle' />
            </td>
          </tr>
          <tr>
            <td colspan="3"><a href="javascript:void(0)" onclick="adminLogin()" >登录</a></td>
          </tr>
        </table>
      </div>
    </div>
    <div class="login_dy"></div>
    </div>
  </div>
</div>
<div class="login_bottom">
<div class="bottom_bj">主办单位：论文检测办公室 版权所有：zlking工作室<br>技术支持：zlking工作室 技术热线：028-67167178 ICP备案号：苏ICP备12070094号</div>
</div>
</body>
</html>
