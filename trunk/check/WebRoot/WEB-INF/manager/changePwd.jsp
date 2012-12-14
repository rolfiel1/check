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
<title>百分百检测系统--修改密码</title>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/js/jqueryUI/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/js/jqueryUI/themes/icon.css">
<script language="javascript" src="<%=basePath%>/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/jqueryUI/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/jqueryUI/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
<!--加载开始------------------>
	$().ready(function(){
		$('#changePwdWindow').window('open');
	
	});
	function changePwd(){
		var oldPwd=$('#oldPwd').val().replace(/(^\s*)|(\s*$)/g, "");
		var newPwd=$('#newPwd').val().replace(/(^\s*)|(\s*$)/g, "");
		var aginPwd=$('#aginPwd').val().replace(/(^\s*)|(\s*$)/g, "");
		if(oldPwd==""){
			$.messager.alert("消息提示",'请输入旧密码!','info');
		}else if(newPwd==""){
			$.messager.alert("消息提示",'请输入新密码!','info');
		}else if(aginPwd==""){
			$.messager.alert("消息提示",'请再次输入新密码!','info');
		}else if(newPwd!=aginPwd){
			$.messager.alert("消息提示",'两次输入不一致,请重新输入!','info');
		}else{
			$.ajax({
				url: "user!changeP.action",
				data:{"oldPwd":oldPwd,"newPwd":newPwd},
				type: "post",
				dataType:"json",
				success: function(data) {
					if(data['ret']=="success"){
						$('#changePwdWindow').window('close');
						$.messager.alert("消息提示",data['msg'],'info');
						setTimeout("window.location.href='<%=basePath%>user!logout.action'",5000);
					}else{
						 $.messager.alert("消息提示",data['msg'],'info');
					}		
				}
			});
		}
	
	}
	function closeWin(){
		$('#oldPwd').val('');
		$('#newPwd').val('');
		$('#aginPwd').val('');
		$('#changePwdWindow').window('close');
	}
</script>
</head>
<body >
 	<div id="changePwdWindow" class="easyui-window" title="修改密码" iconCls="icon-add" closed="true" modal="true" style="width:300px;height:200px;padding:5px;background: #fafafa;">
		<div>原&nbsp;密&nbsp;码:<input type="password" id="oldPwd"/></div>
		<div>新&nbsp;密&nbsp;码:<input type="password" id="newPwd"/></div>
		<div>重复密码:<input type="password" id="aginPwd"/></div>
		<div>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="changePwd()">确定</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeWin()">关闭</a>
		</div>
	</div>
</body>
</html>
