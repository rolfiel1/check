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
<title>PaperPass检测系统</title>
<link href="css/index.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="js/motel.js"></script>
<script language="javascript" src="js/jquery-1.8.1.min.js"></script>

<script type="text/javascript">
	function show() {
		var a = document.getElementById("sz").value;
		var sum = Math.ceil(a.length / 2000);
		document.getElementById("le").innerHTML = a.length;
		document.getElementById("le2").innerHTML = sum;

	}
</script>
</head>

<body>
	<div class="box">
		<div class="lwjc_top">
			<div class="gnl">
				<ul>
					<li><a href="#"><span class="tb_4"></span>ENGLISH</a>
					</li>
					<li><a href="#"><span class="tb_3"></span>联系我们</a>
					</li>
					<li><a href="#"><span class="tb_2"></span>加入我们</a>
					</li>
					<li><a href="#"><span class="tb_1"></span>登录|注册</a>
					</li>
				</ul>
			</div>
			<ul class="lwjc_dh">
				<li><a href="#">首页</a>
				</li>
				<li><a href="#">网络检测</a>
				</li>
				<li><a href="#">论文检测</a>
				</li>
				<li><a href="#">期刊检测</a>
				</li>
				<li><a href="#">收费充值</a>
				</li>
				<li><a href="#">文档转换</a>
				</li>
				<li><a href="#">论文修改</a>
				</li>
				<li><a href="#">关于我们</a>
				</li>
			</ul>
		</div>
		<div class="lwjc_gg"></div>
		<div class="lwjc_lct"></div>
		<div class="lwjc_srk">
			<div class="srk_bt">重要提醒：系统仅支持中文论文（包括简体，繁体）的检测，暂不支持英语等非中文的检测请勿提交非中文论文！！</div>
			<div class="table_box" style="text-align:center;">
			<form action="paperpass!ppCheck.action?orderNo1=$('#validCode').val()" method="post">
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
						<td>（内容长度最少<span>2000</span>字，至多<span>10</span>万字) 当前以输入（<span
							id="le">0</span>字）您需要拍下对应宝贝的数量（<span id="le2">0</span>件）</td>
					</tr>
					<tr>
						<td><a href="javascript:void(0)" onclick="testMessageBox(event);">开始检测</a>
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

		<div class="lwjc_bottom">
			<div class="top_lb">
				<div class="top_cjwt">
					<ul>
						<li class="cjwt_bt"><span class="bottom_bt">常见问题</span><span
							class="bottom_tb"></span>
						</li>
						<li><a href="#">常见问题常见问题常见问题常见问题</a>
						</li>
						<li><a href="#">常见问题常见问题常见问题常见问题</a>
						</li>
						<li><a href="#">常见问题常见问题常见问题常见问题</a>
						</li>
						<li><a href="#">常见问题常见问题常见问题常见问题</a>
						</li>
					</ul>
				</div>
				<div class="top_gycx">
					<ul>
						<li class="cjwt_bt"><span class="bottom_bt">常见问题</span><span
							class="bottom_tb"></span>
						</li>
						<li><a href="#">常见问题常见问题常见问题常见问题</a>
						</li>
						<li><a href="#">常见问题常见问题常见问题常见问题</a>
						</li>
						<li><a href="#">常见问题常见问题常见问题常见问题</a>
						</li>
						<li><a href="#">常见问题常见问题常见问题常见问题</a>
						</li>
					</ul>
				</div>
				<div class="top_gylc">
					<ul>
						<li class="cjwt_bt"><span class="bottom_bt">常见问题</span><span
							class="bottom_tb"></span>
						</li>
						<li><a href="#">常见问题常见问题常见问题常见问题</a>
						</li>
						<li><a href="#">常见问题常见问题常见问题常见问题</a>
						</li>
						<li><a href="#">常见问题常见问题常见问题常见问题</a>
						</li>
						<li><a href="#">常见问题常见问题常见问题常见问题</a>
						</li>
					</ul>
				</div>
				<div class="top_sfsm">
					<ul>
						<li class="cjwt_bt"><span class="bottom_bt">常见问题</span><span
							class="bottom_tb"></span>
						</li>
						<li><a href="#">常见问题常见问题常见问题常见问题</a>
						</li>
						<li><a href="#">常见问题常见问题常见问题常见问题</a>
						</li>
						<li><a href="#">常见问题常见问题常见问题常见问题</a>
						</li>
						<li><a href="#">常见问题常见问题常见问题常见问题</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="lwjc_bqxx">
				<ul>
					<li>关于网站 | 检测帮助 | 免责声明 | 合作伙伴 | 友情连接 | 加入我们 | 联系方式</li>
					<li>Copyright 2010 zhe.org.All Rights Reserved</li>
					<li>本站联系地址：湖南益阳市河东区解放路28号 本站联系电话：4006083200</li>
					<li>版权所有：友情链接: hi7网 中国论文检测网 华农电子商务协会 衡阳师范学院论坛 湖北第二师范学院自考
						拷克论文检测 论文发表</li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>
