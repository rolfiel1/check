<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>百分百检测系统--首页</title>
<link href="css/index.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div class="box">
<%@ include file="top.jsp"%>
<div class="lwjc_gdtp">
<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="970" height="347">
  <param name="movie" value="imageshow.swf" />
  <param name="quality" value="high" />
  <embed src="imageshow.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="970" height="347"></embed>
</object> 
</div>
<div class="lwjc_info">
  <div class="info_left">
    <div class="left_nr">
      <table width="100%" border="0">
        <tr>
          <td>&nbsp;</td>
          <td class="nr_bt">论文检测系统</td>
        </tr>
        <tr>
          <td class="nr_logo"><img src="images/wf_logo_s1.gif" width="75" height="106" /></td>
          <td class="nr_info">万方自主研发的“基于滑动窗口的低频特征部分匹配算法”
            能准确识别细微改动,兼顾查全，查准.基于万方公司所收录
            的期刊论文，学位论文等海量数据，运用先进检测研制而成，
            具有检测速度快，检测准确等特点，官网检测单位价10元，
            这里仅需9元，便宜超值，适合论文歉意的修改检测！！</td>
        </tr>
        <tr>
          <td colspan="2" class="nr_gmxx">【<span style="color:red">查看：</span>检测报告样本】</td>
        </tr>
        <tr>
          <td colspan="2" class="nr_gmxx"><span class="nr_xq"><a href="#">详情</a></span>【价格：9元/万字数】</td>
        </tr>
        <tr>
          <td colspan="2" class="nr_gmxx"><span class="nr_xq"><a href="#">点击购买</a></span>【时间：1-5分钟可出报告单，立等可出】</td>
        </tr>
      </table>
    </div>
    <div class="left_nr">
      <table width="100%" border="0">
        <tr>
          <td>&nbsp;</td>
          <td class="nr_bt">万方相识论文检测系统</td>
        </tr>
        <tr>
          <td class="nr_logo"><img src="images/wf_logo_s1.gif" width="75" height="106" /></td>
          <td class="nr_info">万方自主研发的“基于滑动窗口的低频特征部分匹配算法”
            能准确识别细微改动,兼顾查全，查准.基于万方公司所收录
            的期刊论文，学位论文等海量数据，运用先进检测研制而成，
            具有检测速度快，检测准确等特点，官网检测单位价10元，
            这里仅需9元，便宜超值，适合论文歉意的修改检测！！</td>
        </tr>
        <tr>
          <td colspan="2" class="nr_gmxx">【<span style="color:red">查看：</span>检测报告样本】</td>
        </tr>
        <tr>
          <td colspan="2" class="nr_gmxx"><span class="nr_xq"><a href="#">详情</a></span>【价格：9元/万字数】</td>
        </tr>
        <tr>
          <td colspan="2" class="nr_gmxx"><span class="nr_xq"><a href="wanfang!wf.action">点击购买</a></span>【时间：1-5分钟可出报告单，立等可出】</td>
        </tr>
      </table>
    </div>
    <div class="left_nr">
      <table width="100%" border="0">
        <tr>
          <td>&nbsp;</td>
          <td class="nr_bt">知网论文检测系统</td>
        </tr>
        <tr>
          <td class="nr_logo"><img src="images/wf_logo_s1.gif" width="75" height="106" /></td>
          <td class="nr_info">万方自主研发的“基于滑动窗口的低频特征部分匹配算法”
            能准确识别细微改动,兼顾查全，查准.基于万方公司所收录
            的期刊论文，学位论文等海量数据，运用先进检测研制而成，
            具有检测速度快，检测准确等特点，官网检测单位价10元，
            这里仅需9元，便宜超值，适合论文歉意的修改检测！！</td>
        </tr>
        <tr>
          <td colspan="2" class="nr_gmxx">【<span style="color:red">查看：</span>检测报告样本】</td>
        </tr>
        <tr>
          <td colspan="2" class="nr_gmxx"><span class="nr_xq"><a href="#">详情</a></span>【价格：9元/万字数】</td>
        </tr>
        <tr>
          <td colspan="2" class="nr_gmxx"><span class="nr_xq"><a href="#">点击购买</a></span>【时间：1-5分钟可出报告单，立等可出】</td>
        </tr>
      </table>
    </div>
  </div>
  <div class="info_right">
    <div class="left_nr">
      <table width="100%" border="0">
        <tr>
          <td>&nbsp;</td>
          <td class="nr_bt">知网论文检测系统</td>
        </tr>
        <tr>
          <td class="nr_logo"><img src="images/wf_logo_s1.gif" width="75" height="106" /></td>
          <td class="nr_info">万方自主研发的“基于滑动窗口的低频特征部分匹配算法”
            能准确识别细微改动,兼顾查全，查准.基于万方公司所收录
            的期刊论文，学位论文等海量数据，运用先进检测研制而成，
            具有检测速度快，检测准确等特点，官网检测单位价10元，
            这里仅需9元，便宜超值，适合论文歉意的修改检测！！</td>
        </tr>
        <tr>
          <td colspan="2" class="nr_gmxx">【<span style="color:red">查看：</span>检测报告样本】</td>
        </tr>
        <tr>
          <td colspan="2" class="nr_gmxx"><span class="nr_xq"><a href="#">详情</a></span>【价格：9元/万字数】</td>
        </tr>
        <tr>
          <td colspan="2" class="nr_gmxx"><span class="nr_xq"><a href="#">点击购买</a></span>【时间：1-5分钟可出报告单，立等可出】</td>
        </tr>
      </table>
    </div>
    <div class="left_nr">
      <table width="100%" border="0">
        <tr>
          <td>&nbsp;</td>
          <td class="nr_bt">知网论文检测系统</td>
        </tr>
        <tr>
          <td class="nr_logo"><img src="images/wf_logo_s1.gif" width="75" height="106" /></td>
          <td class="nr_info">万方自主研发的“基于滑动窗口的低频特征部分匹配算法”
            能准确识别细微改动,兼顾查全，查准.基于万方公司所收录
            的期刊论文，学位论文等海量数据，运用先进检测研制而成，
            具有检测速度快，检测准确等特点，官网检测单位价10元，
            这里仅需9元，便宜超值，适合论文歉意的修改检测！！</td>
        </tr>
        <tr>
          <td colspan="2" class="nr_gmxx">【<span style="color:red">查看：</span>检测报告样本】</td>
        </tr>
        <tr>
          <td colspan="2" class="nr_gmxx"><span class="nr_xq"><a href="#">详情</a></span>【价格：9元/万字数】</td>
        </tr>
        <tr>
          <td colspan="2" class="nr_gmxx"><span class="nr_xq"><a href="#">点击购买</a></span>【时间：1-5分钟可出报告单，立等可出】</td>
        </tr>
      </table>
    </div>
    <div class="left_nr">
      <table width="100%" border="0">
        <tr>
          <td>&nbsp;</td>
          <td class="nr_bt">知网论文检测系统</td>
        </tr>
        <tr>
          <td class="nr_logo"><img src="images/wf_logo_s1.gif" width="75" height="106" /></td>
          <td class="nr_info">万方自主研发的“基于滑动窗口的低频特征部分匹配算法”
            能准确识别细微改动,兼顾查全，查准.基于万方公司所收录
            的期刊论文，学位论文等海量数据，运用先进检测研制而成，
            具有检测速度快，检测准确等特点，官网检测单位价10元，
            这里仅需9元，便宜超值，适合论文歉意的修改检测！！</td>
        </tr>
        <tr>
          <td colspan="2" class="nr_gmxx">【<span style="color:red">查看：</span>检测报告样本】</td>
        </tr>
        <tr>
          <td colspan="2" class="nr_gmxx"><span class="nr_xq"><a href="#">详情</a></span>【价格：9元/万字数】</td>
        </tr>
        <tr>
          <td colspan="2" class="nr_gmxx"><span class="nr_xq"><a href="#">点击购买</a></span>【时间：1-5分钟可出报告单，立等可出】</td>
        </tr>
      </table>
    </div>
  </div>
  <div style="clear:both"></div>
</div>
<%@ include file="bottom.jsp"%>
</div>
</body>
</html>
