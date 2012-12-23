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
<title>百分百检测系统--客户列表页面</title>
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
	 showData('user!ajaxClientList.action');
})

function showData(url){
	$('#dataList').datagrid({
		title:'检测用户列表',
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
			{field:'username',title:'账号',align:'center',width:150,sortable:true},
			{field:'flag',title:'检测类型',align:'center',width:90,sortable:true,
				formatter:function(value,rowData,rowIndex){
					if(rowData.flag ==1){
						return "Paperpass";
					}else if(rowData.flag ==2){
						return "万方";
					}else{
						return "知网";
					}
				}
			},
			{field:'cd',title:'创建时间',align:'center',width:90,sortable:true},
			{field:'ld',title:'登录时间',align:'center',width:90,sortable:true}
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
						url: "user!delete.action?ids="+ids,
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
<bod>
	<div id="dataList"></div>
</body>
</html>
