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
<title>PaperPass检测系统--结果页面</title>
<link href="css/index.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="js/jqueryUI/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="js/jqueryUI/themes/icon.css">
<script language="javascript" src="js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="js/jqueryUI/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/jqueryUI/locale/easyui-lang-zh_CN.js"></script>
<script language="javascript">
$(document).ready(function(){
	 showData('paperpass!ajaxList.action?id='${loginUserId});
})

function showData(url){
	$('#dataList').datagrid({
		width:650,
		height:'auto',
		nowrap: false,//True 就会把数据显示在一行里
		collapsible:false,
		url:url,//数据来源
		sortName: 'time',//排序字段
		sortOrder: 'desc',
		remoteSort: false,
		method:'post',
		idField:'id',
		columns:[[
			{field:'id',width:0,hidden:true, sortable:true},
			{field:'title',title:'标题',align:'center',width:90,sortable:true},
			{field:'author',title:'作者',align:'center',width:70,sortable:true},
			{field:'link',title:'报告',align:'center',width:70,sortable:true,
				formatter:function(value,rowData,rowIndex){
					return "<a style=\"cursor:pointer;\" onclick=\"javascript:window.open('user!showMess.action?uid="+rowData.id+"','','width=430,height=510')\"      >"+rowData.name+"</a>";
				}},
			{field:'create_date',title:'上传时间',align:'center',width:213,sortable:true}
		]],
		pagination:true//显示分页栏
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
</script>
</head>
<body style="background:none;">
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
  <div class="center_bj1">
  <div class="left_info">
  <div class="tb_box"><table width="100%" border="0">
  <tr>
    <td width="16"><span class="user_tb"></span></td>
    <th align="right" width="70">当前用户：</th>
    <td>1235161635123135156</td>
  </tr>
  <tr>
    <td><span class="yhxt_tb"></span></td>
    <th align="right">检测系统：</th>
    <td>织梦系统</td>
  </tr>
</table>
</div>

  </div>
  <div class="right_info">
  <div class="tble_box">
  <div id="dataList"></div>
  
  <!--  <table width="100%" border="0">
  <tr>
    <td class="info_bt" colspan="2">下载列表</td>
  </tr>
  <tr>
    <td colspan="2" class="info_lb"><a href="#">论文下载列表论文下载列表论文下</a></td>
    </tr>  
     <tr>
    <td colspan="2" class="info_lb"><a href="#">论文下载列表论文下载列表论文下</a></td>
    </tr> 
     <tr>
    <td colspan="2" class="info_lb"><a href="#">论文下载列表论文下载列表论文下</a></td>
    </tr> 
     <tr>
    <td colspan="2" class="info_lb"><a href="#">论文下载列表论文下载列表论文下</a></td>
    </tr> 
</table>
-->
</div>
</div>
    </div>
  </div>
<div class="login_bottom">
<div class="bottom_bj">主办单位：论文检测办公室 版权所有：zlking工作室<br>技术支持：zlking工作室 技术热线：028-67167178 ICP备案号：苏ICP备12070094号</div>
</div>
</body>
</html>
