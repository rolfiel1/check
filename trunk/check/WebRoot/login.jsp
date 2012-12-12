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
<title>百分百论文检测系统--登录界面</title>
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
        <li><a href="#">首页</a></li>
        <li><a href="#">知网系统</a></li>
        <li><a href="#">万方系统</a></li>
        <li><a href="#">paperpass系统</a></li>
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
            <td class="th">订单号：</td>
            <td><input type="text" name="orderNo1" id="orderNo1"  class="wbk"/>
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
            <td colspan="3"><a href="#">购买</a><a href="javascript:void(0)" onclick="login()" >登录</a></td>
          </tr>
          <tr>
            <td colspan="3" class="zs"><span style="color:#f00">*</span>为必填项，如无订单号请点击购买！</td>
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
