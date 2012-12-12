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
<title>百分百检测系统--文章[添加/修改]页面</title>
<script charset="utf-8" src="<%=basePath%>js/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="<%=basePath%>js/kindeditor/lang/zh_CN.js"></script>
<script>
	var editor;
	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="content"]', {
			allowFileManager : true
		});
		K('input[name=getHtml]').click(function(e) {
			alert(editor.html());
		});
		K('input[name=isEmpty]').click(function(e) {
			alert(editor.isEmpty());
		});
		K('input[name=getText]').click(function(e) {
			alert(editor.text());
		});
		K('input[name=selectedHtml]').click(function(e) {
			alert(editor.selectedHtml());
		});
		K('input[name=setHtml]').click(function(e) {
			editor.html('<h3>Hello KindEditor</h3>');
		});
		K('input[name=setText]').click(function(e) {
			editor.text('<h3>Hello KindEditor</h3>');
		});
		K('input[name=insertHtml]').click(function(e) {
			editor.insertHtml('<strong>插入HTML</strong>');
		});
		K('input[name=appendHtml]').click(function(e) {
			editor.appendHtml('<strong>添加HTML</strong>');
		});
		K('input[name=clear]').click(function(e) {
			editor.html('');
		});
	});
</script>
</head>
<body>
	<h3>默认模式</h3>
	<form>
		<textarea name="content"
			style="width:800px;height:400px;visibility:hidden;">KindEditor</textarea>
		<p>
			<input type="button" name="getHtml" value="取得HTML" /> <input
				type="button" name="isEmpty" value="判断是否为空" /> <input type="button"
				name="getText" value="取得文本(包含img,embed)" /> <input type="button"
				name="selectedHtml" value="取得选中HTML" /> <br /> <br /> <input
				type="button" name="setHtml" value="设置HTML" /> <input type="button"
				name="setText" value="设置文本" /> <input type="button"
				name="insertHtml" value="插入HTML" /> <input type="button"
				name="appendHtml" value="添加HTML" /> <input type="button"
				name="clear" value="清空内容" /> <input type="reset" name="reset"
				value="Reset" />
		</p>
	</form>
<body>
</body>
</html>
