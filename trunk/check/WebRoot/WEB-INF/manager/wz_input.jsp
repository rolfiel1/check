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
<script charset="utf-8"
	src="<%=basePath%>js/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="<%=basePath%>js/kindeditor/lang/zh_CN.js"></script>
<script>
	var editor;
	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="article.content"]', {
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
	<form action="article!save.action" method="post">
		文章标题:<input type="text" name="article.title" id="article.title" value="${article.title }"><br /> 
		文章作者:<input type="text" name="article.author" id="article.author" value="${article.author}"><br /> 
		文章分类:<select id=article.type name="article.type">
					<option value="1">第一</option>
					<option value="2">第二</option>
					<option value="3">第三</option>
					<option value="4">第四</option>
			</select><br /> 
		文章内容:<textarea name="article.content" id="article.content"
					style="width:800px;height:400px;visibility:hidden;">${article.content }</textarea> <br />
				文章备注:<textarea rows="2" cols="40" name="article.remark" id="article.remark ">${article.remark }</textarea>
		<input type="submit" name="submitBtn" id="submitBtn" value="提交"><input type="reset" name="resetBtn" id="resetBtn" value="取消">		
	</form>
</body>
</html>
