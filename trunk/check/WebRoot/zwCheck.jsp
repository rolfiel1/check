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
<title>知网检测系统--知网检测</title>
<link href="<%=basePath%>/css/index.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="<%=basePath%>/js/motel.js"></script>
<script language="javascript" src="<%=basePath%>/js/jquery-1.8.1.min.js"></script>

<script type="text/javascript">
	function show() {
		var a = document.getElementById("sz").value;
		var sum = Math.ceil(a.length / 200);
		document.getElementById("le").innerHTML = a.length;
		document.getElementById("le2").innerHTML = sum;

	}
</script>
</head>

<body>
	<div class="box">
<%@ include file="top.jsp"%>
		<div class="lwjc_gg"></div>
		<div class="lwjc_lct"></div>
		<div class="lwjc_srk">
			<div class="srk_bt">重要提醒：系统仅支持中文论文（包括简体，繁体）的检测，暂不支持英语等非中文的检测请勿提交非中文论文！！</div>
			<div class="table_box" style="text-align:center;">
			<form id="zwForm" action="zw!check.action" method="post">
				<table width="100%" border="0" class="table_tb">
					<tr>
						<td colspan="2">提示：系统在检测时，会分析论文的前后文关系，所以请您提交论文的全部内容，如果是非全文的检测将不能保证检测的准确性！</td>
					</tr>
					<tr>
						<th>论文标题：</th>
						<td><input type="text" name="title" id="title" />
							(*可选长度小于30字)</td>
					</tr>
					<tr>
						<th>论文作者：</th>
						<td><input type="text" name="author" id="author" />
							(*可选长度小于15字)</td>
					</tr>
					<tr>
						<th>论文内容：</th> 
						<td>（内容长度最少<span>200</span>字) 当前以输入（<span
							id="le">0</span>字）您需要拍下对应宝贝的数量（<span id="le2">0</span>件）</td>
					</tr>
					<tr>
						<td><a href="javascript:void(0)" onclick="testMessageBoxzw(event);">开始检测</a>
						</td>
						<td>您现在提交检测，大概需要<span>5</span>分钟。</td>
					</tr>
					<tr>
						<td colspan="2"><textarea name="sz" cols="" rows=""
								class="wb_srk" id="sz" onkeyup="show()"></textarea>
						</td>
					</tr>
				</table>
				</form>
			</div>
		</div>
<%@ include file="bottom.jsp"%>
	</div>
</body>
</html>
