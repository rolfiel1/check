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
<title>检测系统--结果页面</title>
<link href="<%=basePath%>/css/index.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/js/jqueryUI/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/js/jqueryUI/themes/icon.css">
<script language="javascript" src="<%=basePath%>/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/jqueryUI/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/jqueryUI/locale/easyui-lang-zh_CN.js"></script>
<script language="javascript">
$(document).ready(function(){
	 showData('report!ajaxList.action?id=${user.username}');
	 setTimeout("refresh()", 5000);
})

function refresh(){
	$('#dataList').datagrid('reload');
}

function showData(url){
	$('#dataList').datagrid({
		title:'论文检测报告',
		width:'auto',
		height:'auto',
		nowrap: false,//True 就会把数据显示在一行里
		collapsible:false,
		url:url,//数据来源
		sortName: 'create_date',//排序字段
		sortOrder: 'desc',
		remoteSort: false,
		method:'post',
		idField:'id',
		frozenColumns:[[
	    	{field:'ck',checkbox:true}
		]],
		columns:[[
			{field:'id',width:10,hidden:true, sortable:true},
			{field:'title',title:'标题',align:'center',width:90,sortable:true},
			{field:'author',title:'作者',align:'center',width:90,sortable:true},
			{field:'sign',title:'类型',align:'center',width:30,sortable:true,
				formatter:function(value,rowData,rowIndex){
					if(value ==1){
						return "Paperpass";
					}else if(value ==2){
						return "万方";
					}else{
						return "知网";
					}
				}
			},
			{field:'link',title:'报告',align:'center',width:150,sortable:true,
				formatter:function(value,rowData,rowIndex){
					if(rowData.link!='underchecking'){
						if(parseFloat(${user.account})>=parseFloat(rowData.need_price)){
							return "<a href=\"<%=basePath%>downloadTemp/"+value+".zip \" style=\"cursor:pointer;\" >下载报告</a>";
						}else{
							return "账号检测费用不足,无法下载";
						}
					}else{
						return "正在检测,请稍后...";
					}
				}},
			{field:'cd',title:'提交时间',align:'center',width:90,sortable:true},
			{field:'need_price',title:'检测费用',align:'center',width:50,sortable:true}
		]],
		pagination:true,//显示分页栏
		rownumbers:true,//自动给数据编号
		toolbar:[{
			id:'btncut',
			text:'删除',
			iconCls:'icon-remove',
			handler:function(){
				var ids=del();
			}
		}]
	});
	var p = $('#dataList').datagrid('getPager');
	//设置分页控件  
	if (p){
    	$(p).pagination({  
       		pageSize: 10,//每页显示的记录条数，默认为10  
        	pageList: [10,20,30],//可以设置每页记录条数的列表  
        	beforePageText: '第',//页数文本框前显示的汉字  
        	afterPageText: '页    共 {pages} 页',  
        	displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' 
    	});  
	}
}	
	function del(){
		var ids = [];
		var rows = $('#dataList').datagrid('getSelections');
		if(rows.length>0){
			for(var i=0;i<rows.length;i++){
				ids.push(rows[i].id);
			}
			$.messager.confirm('提示', '是否删除选择的数据?', function(ret){
				if(ret){
					$.ajax({
						url: "report!delete.action?ids="+ids,
						type: "POST",
						dataType:"json",
						success: function(data) {
							$.messager.alert("消息提示",data['msg'],'info');
							$('#dataList').datagrid('reload');
						}
					});
				}
			}); 
		}else{
			$.messager.alert("消息提示","请选择数据!",'info');
		}
	}
</script>
</head>
<body style="background:none;">
	<div class="login_top">
		<div class="center_bj">
			<div class="xtdh">
				<ul>
					<li><a href="<%=basePath%>index.jsp">首页</a>
					</li>
					<li><a href="<%=basePath%>ppCheck.jsp">paperpass系统</a>
					</li>
					<li><a href="<%=basePath%>wfCheck.jsp">知网系统</a>
					</li>
					<li><a href="#">知网系统</a>
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
							<th align="right" width="70">当前用户:</th>
							<td>${user.username }&nbsp;&nbsp;余额:${user.price}元</td>
						</tr>
						<tr>
							<td><span class="yhxt_tb"></span>
							</td>
							<th align="right">检测系统:</th>
							<td>百分百论文检测系统</td>
						</tr>
						<tr>
							<td><span class="yhxt_tb"></span>
							</td>
							<th align="right">系统操作:</th>
							<td><a href="">退出系统</a></td>
						</tr>
					</table>
				</div>

			</div>
			<div class="right_info">
				<div id="dataList"></div>
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
