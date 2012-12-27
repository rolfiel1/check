<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script language="javascript" src="<%=basePath%>/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		for(var i=1;i<=4;i++){
			$.ajax({
				url: "article!show4.action?type="+i,
				type: "POST",
				dataType:"json",
				success: function(data) {
					if(data!=null&&data[0]['type']==1){
						for(var j=0;j<data.length;j++){
							$(".top_cjwt >ul").append("<li><a href='<%=basePath%>article!detail.action?id="+data[j]['id']+"'>"+data[j]['title']+"</a></li>");
						}
					}
					if(data!=null&&data[0]['type']==2){
						for(var j=0;j<data.length;j++){
							$(".top_gycx >ul").append("<li><a href='<%=basePath%>article!detail.action?id="+data[j]['id']+"'>"+data[j]['title']+"</a></li>");
						}
					}
					if(data!=null&&data[0]['type']==3){
						for(var j=0;j<data.length;j++){
							$(".top_gylc >ul").append("<li><a href='<%=basePath%>article!detail.action?id="+data[j]['id']+"'>"+data[j]['title']+"</a></li>");
						}
					}
					if(data!=null&&data[0]['type']==4){
						for(var j=0;j<data.length;j++){
							$(".top_sfsm >ul").append("<li><a href='<%=basePath%>article!detail.action?id="+data[j]['id']+"'>"+data[j]['title']+"</a></li>");
						}
					}
				}
			});
		}
	})
</script>
<div class="lwjc_bottom">
  <div class="top_lb">
    <div class="top_cjwt">
      <ul>
        <li class="cjwt_bt"><span class="bottom_bt">常见问题</span><span class="bottom_tb"></span></li>
      </ul>
    </div>
    <div class="top_gycx">
      <ul>
        <li class="cjwt_bt"><span class="bottom_bt">常见问题</span><span class="bottom_tb"></span></li>
      </ul>
    </div>
    <div class="top_gylc">
      <ul>
        <li class="cjwt_bt"><span class="bottom_bt">常见问题</span><span class="bottom_tb"></span></li>
      </ul>
    </div>
    <div class="top_sfsm">
      <ul>
        <li class="cjwt_bt"><span class="bottom_bt">常见问题</span><span class="bottom_tb"></span></li>
      </ul>
    </div>
  </div>
  <div class="lwjc_bqxx">
    <ul>
      <li> 关于网站 | 检测帮助 | 免责声明 | 合作伙伴 | 友情连接 | 加入我们 | 联系方式</li>
      <li>Copyright 2010 zhe.org.All Rights Reserved</li>
      <li>本站联系地址：湖南益阳市河东区解放路28号 本站联系电话：4006083200 </li>
      <li>版权所有：友情链接: hi7网 中国论文检测网 华农电子商务协会 衡阳师范学院论坛 湖北第二师范学院自考 拷克论文检测 论文发表</li>
    </ul>
  </div>
</div>