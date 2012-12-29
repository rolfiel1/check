package com.check.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class WFUtil implements ApplicationContextAware, DisposableBean{
	
	private static Logger logger = Logger.getLogger(WFUtil.class);
	
	private static ApplicationContext applicationContext = null;
	
	private static String wfName = PPUtil.getProp("wf.user");
	
	private static String wfPwd = PPUtil.getProp("wf.pwd");

	public static String check(String content) throws HttpException, IOException {
		String reportNames="";
		HttpClient httpClient = new HttpClient();
		DefaultHttpParams.getDefaultParams().setParameter( "http.protocol.cookie-policy",CookiePolicy.BROWSER_COMPATIBILITY);
		//第一次请求页面
		String url = "http://login.wanfangdata.com.cn/Login.aspx?ReturnUrl=http%3a%2f%2fcheck.wanfangdata.com.cn%2fUploadPaper.aspx&needLoginAccountType=Person";
		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Accept","image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		getMethod.setRequestHeader("Accept-Language", "zh-cn");
		getMethod.setRequestHeader("Connection", "Keep-Alive");
		getMethod.setRequestHeader("Host", "login.wanfangdata.com.cn");
		getMethod.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
		int code = httpClient.executeMethod(getMethod);
		//返回页面状态代码
		logger.info("第一次请求返回状态码："+code);
		//获得页面内容
//		System.out.println("第一次请求返回页面内容"+getMethod.getResponseBodyAsString());
		
		//获得页面cookie
		Header header = getMethod.getResponseHeader("Set-cookie");
		//获得sessionId
		String headerCookie = header.getValue();
		String SessionIds = headerCookie.substring(headerCookie.indexOf("NET_SessionId=")+ "NET_SessionId=".length());
		String sessionId=SessionIds.substring(0,SessionIds.indexOf(";"));
		String cookie1=header.getValue().substring(0,header.getValue().indexOf("domain"));
		//拼接第二次连接用cookie
		Date date1=new Date();
		StringBuffer sb = new StringBuffer();
        sb.append(cookie1);  
		sb.append("Hm_lvt_f5e6bd27352a71a202024e821056162b="+date1.getTime()+";");
		sb.append("Hm_lpvt_f5e6bd27352a71a202024e821056162b="+date1.getTime()+";");
		sb.append("ASP.NET_SessionId=").append(sessionId);
		//cookie内容
		logger.info("第二次连接用cookie=="+sb.toString());
		Document doc = Jsoup.parse(getMethod.getResponseBodyAsString());
		//__VIEWSTATE
		String __VIEWSTATE=doc.getElementById("__VIEWSTATE").val();
        //释放连接
        getMethod.releaseConnection();
        
        //第二次调用，登录发送post请求
        httpClient.getHostConfiguration().setHost("login.wanfangdata.com.cn", 80, "http");
        PostMethod method = new PostMethod("/Login.aspx?ReturnUrl=http%3a%2f%2fcheck.wanfangdata.com.cn%2fUploadPaper.aspx&needLoginAccountType=Person");
		NameValuePair[] nvp = { 
				new NameValuePair("__VIEWSTATE", __VIEWSTATE),
				new NameValuePair("userid", wfName),
				new NameValuePair("password",wfPwd),
				new NameValuePair("login", "%E7%99%BB%E5%BD%95")
				};
		method.setRequestBody(nvp);
		method.setRequestHeader("Accept","image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		method.setRequestHeader("Accept-Language", "zh-cn");
		method.setRequestHeader("Referer","http://login.wanfangdata.com.cn/Login.aspx?ReturnUrl=http%3a%2f%2fcheck.wanfangdata.com.cn%2fUploadPaper.aspx&needLoginAccountType=Person");
		method.setRequestHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
		method.setRequestHeader("Host", "login.wanfangdata.com.cn");
		method.setRequestHeader("Connection","Keep-Alive");
		method.setRequestHeader("Cookie", sb.toString());
		httpClient.executeMethod(method);
		//返回连接状态，302跳转页面
		logger.info("第2次登录返回状态码："+method.getStatusCode());
//		logger.info(method.getResponseBodyAsString());
		if(302==method.getStatusCode()){
			Header[] headers =method.getResponseHeaders("Set-Cookie");
			//第三次连接用的cookie
			String header3=headers[headers.length-1].getValue();
			String cookie3=header3.substring(0, header3.indexOf("domain"));
			StringBuffer sb2 = new StringBuffer();
			sb2.append(cookie3);
			sb2.append("Hm_lvt_f5e6bd27352a71a202024e821056162b="+date1.getTime()+";");
			sb2.append("Hm_lpvt_f5e6bd27352a71a202024e821056162b="+date1.getTime()+";");
			sb2.append("ASP.NET_SessionId=").append(sessionId);
			//第三次用的cookie
			logger.info("第三次用的cookie"+sb2.toString());
			//释放第二次连接
			method.releaseConnection();
			 
			//第三次请求，跳转到检测页面/UploadPaper.aspx
			getMethod=new GetMethod("http://check.wanfangdata.com.cn/UploadPaper.aspx");
			getMethod.setRequestHeader("Accept","image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			getMethod.setRequestHeader("Accept-Language", "zh-cn");
			getMethod.setRequestHeader("Connection", "Keep-Alive");
			getMethod.setRequestHeader("Host", "check.wanfangdata.com.cn");
			getMethod.setRequestHeader("Referer", "http://login.wanfangdata.com.cn/Login.aspx?ReturnUrl=http%3a%2f%2fcheck.wanfangdata.com.cn%2fUploadPaper.aspx&needLoginAccountType=Person");
			getMethod.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
			getMethod.setRequestHeader("Cookie", sb2.toString());
			int code2 = httpClient.executeMethod(getMethod);
			logger.info("第三次请求返回状态"+code2);
			//得到提交内容页面
//			System.out.println("第三次请求返回内容"+getMethod.getResponseBodyAsString());
			getMethod.releaseConnection();
			//第四次请求-- 提交测试数据
			httpClient.getHostConfiguration().setHost("check.wanfangdata.com.cn", 80, "http");
	        PostMethod secondPost = new PostMethod("/UploadPaper.aspx");
	        //测试提交数据
	        String pager="网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试网上商城测试";
			NameValuePair[] nvp2 = { new NameValuePair("paper", pager)};
			secondPost.setRequestBody(nvp2);
			secondPost.setRequestHeader("Accept","image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			secondPost.setRequestHeader("Accept-Language", "zh-cn");
			secondPost.setRequestHeader("Referer","http://login.wanfangdata.com.cn/Login.aspx?ReturnUrl=http%3a%2f%2fcheck.wanfangdata.com.cn%2fUploadPaper.aspx&needLoginAccountType=Person");
			secondPost.setRequestHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
			secondPost.setRequestHeader("Host", "login.wanfangdata.com.cn");
			secondPost.setRequestHeader("Connection","Keep-Alive");
			secondPost.setRequestHeader("Cookie", sb2.toString());
			httpClient.executeMethod(secondPost);
			//第四次请求返回状态码
			logger.info("第四次请求返回状态码:"+secondPost.getStatusCode());
			if(302==secondPost.getStatusCode()){
				Header header2 = secondPost.getResponseHeader("Location");
				if(header2!=null){
					System.out.println(header2.getValue());
					//第五次请求提交跳转
					String fiveUrl=header2.getValue();
					//释放第四次连接
					secondPost.releaseConnection();
					getMethod=new GetMethod(fiveUrl);
					getMethod.setRequestHeader("Accept","image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
					getMethod.setRequestHeader("Accept-Language", "zh-cn");
					getMethod.setRequestHeader("Connection", "Keep-Alive");
					getMethod.setRequestHeader("Host", "tran.wanfangdata.com.cn");
					getMethod.setRequestHeader("Referer", "http://check.wanfangdata.com.cn/UploadPaper.aspx");
					getMethod.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
					getMethod.setRequestHeader("Cookie", sb2.toString());
					int code3 = httpClient.executeMethod(getMethod);
					logger.info("第五次请求返回状态"+code3);
					//得到提交内容页面
//					System.out.println("第五次请求返回内容"+getMethod.getResponseBodyAsString());
					//释放第五次请求连接
					getMethod.releaseConnection();
					//TODO 点击购买
					//拼接第六次请求--购买时的url=第五次请求url中webTransactionRequest的Request部分
					String decode5URL=URLDecoder.decode(fiveUrl);
					String decode5URL_1=decode5URL.substring(decode5URL.indexOf("webTransactionRequest")+"webTransactionRequest".length()+1, decode5URL.indexOf("ReturnUrl")-2);
//					System.out.println(decode5URL_1);
					String sixURL_request_1=decode5URL_1.substring(decode5URL_1.indexOf("Request")+"Request".length()+2);
//					System.out.println(sixURL_request_1);
					String sixRUL_request_2=sixURL_request_1.substring(0,sixURL_request_1.indexOf("TransferOut")+"TransferOut".length()+2);
//					System.out.println(sixRUL_request_2);
					String sixURL_request_3=sixURL_request_1.substring(sixURL_request_1.indexOf("Turnover")-1);
//					System.out.println(sixURL_request_3);
					String sixURL=sixRUL_request_2+"[{\"AccountType\":\"Person\",\"Key\":\""+PPUtil.getProp("wf.user")+"\"}],"+sixURL_request_3;
					logger.info(URLEncoder.encode(sixURL));
					
					//开始第六次请求
					getMethod=new GetMethod("http://tran.wanfangdata.com.cn/Tran.aspx?transactionRequest="+URLEncoder.encode(sixURL));
					getMethod.setRequestHeader("Accept","image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
					getMethod.setRequestHeader("Accept-Language", "zh-cn");
					getMethod.setRequestHeader("x-requested-with", "XMLHttpRequest");
					getMethod.setRequestHeader("Connection", "Keep-Alive");
					getMethod.setRequestHeader("Host", "tran.wanfangdata.com.cn");
					getMethod.setRequestHeader("Referer", fiveUrl);
					getMethod.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
					getMethod.setRequestHeader("Cookie", sb2.toString());
					int code4 = httpClient.executeMethod(getMethod);
					logger.info("第六次请求返回码："+code4);
					logger.info("第六次请求返回页面内容:"+getMethod.getResponseBodyAsString());
					//TODO 记录检测单号
					//单号就是请求URL中的copydetect_部分
					//-----------------------------检测提交完成-----------------------------------------
					reportNames=sixURL.substring(sixURL.lastIndexOf("=")+1);
				}
			}
		}else{
			//释放第二次连接
			method.releaseConnection();
			check(content);
		}
		return reportNames;
	}
	
	//下载检测报告
	public static List<String> viewReport() throws HttpException, IOException{
		List<String> reportNames=new ArrayList<String>();
		HttpClient httpClient = new HttpClient();
		DefaultHttpParams.getDefaultParams().setParameter( "http.protocol.cookie-policy",CookiePolicy.BROWSER_COMPATIBILITY);
		//第一次请求页面
		String url = "http://login.wanfangdata.com.cn/Login.aspx?ReturnUrl=http%3a%2f%2fwww.wanfangdata.com.cn%2fUserService.aspx&needLoginAccountType=Person";
		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Accept","image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		getMethod.setRequestHeader("Accept-Language", "zh-cn");
		getMethod.setRequestHeader("Connection", "Keep-Alive");
		getMethod.setRequestHeader("Host", "login.wanfangdata.com.cn");
		getMethod.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
		int code = httpClient.executeMethod(getMethod);
		//返回页面状态代码
		logger.info("第一次请求返回状态码："+code);
		//获得页面内容
//		logger.info("第一次请求返回页面代码"+getMethod.getResponseBodyAsString());
		//获得页面cookie
		Header header = getMethod.getResponseHeader("Set-cookie");
//		System.out.println(header.getValue());
		//获得转码后的cookie
//		logger.info(URLDecoder.decode(header.getValue()));
		//获得sessionId
		String headerCookie = header.getValue();
		String SessionIds = headerCookie.substring(headerCookie.indexOf("NET_SessionId=")+ "NET_SessionId=".length());
		String sessionId=SessionIds.substring(0,SessionIds.indexOf(";"));
		String cookie1=header.getValue().substring(0,header.getValue().indexOf("domain"));
		//拼接第二次连接用cookie
		Date date1=new Date();
		StringBuffer sb = new StringBuffer();
        sb.append(cookie1);  
		sb.append("Hm_lvt_f5e6bd27352a71a202024e821056162b="+date1.getTime()+";");
		sb.append("Hm_lpvt_f5e6bd27352a71a202024e821056162b="+date1.getTime()+";");
		sb.append("ASP.NET_SessionId=").append(sessionId);
		//cookie内容
		logger.info("拼接的cookie=="+sb.toString());
		Document doc = Jsoup.parse(getMethod.getResponseBodyAsString());
		//__VIEWSTATE
		String __VIEWSTATE=doc.getElementById("__VIEWSTATE").val();
        //释放连接
        getMethod.releaseConnection();
        //第二次请求登录
        httpClient.getHostConfiguration().setHost("login.wanfangdata.com.cn", 80, "http");
        PostMethod method = new PostMethod("/Login.aspx?ReturnUrl=http%3a%2f%2fwww.wanfangdata.com.cn%2fUserService.aspx&needLoginAccountType=Person");
		NameValuePair[] nvp = { 
				new NameValuePair("__VIEWSTATE", __VIEWSTATE),
				new NameValuePair("userid", wfName),
				new NameValuePair("password",wfPwd),
				new NameValuePair("login", "%E7%99%BB%E5%BD%95")
				};
		method.setRequestBody(nvp);
		method.setRequestHeader("Accept","image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		method.setRequestHeader("Accept-Language", "zh-cn");
		method.setRequestHeader("Referer","http://login.wanfangdata.com.cn/Login.aspx?ReturnUrl=http%3a%2f%2fwww.wanfangdata.com.cn%2fUserService.aspx&needLoginAccountType=Person");
		method.setRequestHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
		method.setRequestHeader("Host", "login.wanfangdata.com.cn");
		method.setRequestHeader("Connection","Keep-Alive");
		method.setRequestHeader("Cookie", sb.toString());
		httpClient.executeMethod(method);
		//返回连接状态，302跳转页面
		logger.info("第2次登录返回状态码："+method.getStatusCode());
		if(302==method.getStatusCode()){
			Header[] headers =method.getResponseHeaders("Set-Cookie");
			//第三次连接用的cookie
			String header3=headers[headers.length-1].getValue();
			String cookie3=header3.substring(0, header3.indexOf("domain"));
			StringBuffer sb2 = new StringBuffer();
			sb2.append(cookie3);
			sb2.append("Hm_lvt_f5e6bd27352a71a202024e821056162b="+date1.getTime()+";");
			sb2.append("Hm_lpvt_f5e6bd27352a71a202024e821056162b="+date1.getTime()+";");
			sb2.append("ASP.NET_SessionId=").append(sessionId);
			System.out.println("第三次用的cookie"+sb2.toString());
			//释放第二次连接
			method.releaseConnection();
			//第三次连接，跳转到页面/UserService.aspx
			getMethod=new GetMethod("http://tran.wanfangdata.com.cn/MyTransferOutList.aspx");
			getMethod.setRequestHeader("Accept","image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			getMethod.setRequestHeader("Accept-Language", "zh-cn");
			getMethod.setRequestHeader("Connection", "Keep-Alive");
			getMethod.setRequestHeader("Host", "tran.wanfangdata.com.cn");
			getMethod.setRequestHeader("Referer", "http://www.wanfangdata.com.cn/UserService.aspx");
			getMethod.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
			getMethod.setRequestHeader("Cookie", sb2.toString());
			int code2 = httpClient.executeMethod(getMethod);
			logger.info("第三次请求返回状态"+code2);
			//得到报告列表页面
//			System.out.println("第三次请求返回报告列表页面"+getMethod.getResponseBodyAsString());
			//获得页面中所有报告的连接地址
			Document docViewReport = Jsoup.parse(getMethod.getResponseBodyAsString());
			Elements links = docViewReport.select("a[name*=copydetect_]");
			//释放连接
			getMethod.releaseConnection();
			String paras1="";
			String paras2="";
			for(Element ee:links){
				paras1+=ee.attr("name")+",";
			}
			for(Element ee:links){
				paras2+=ee.attr("id")+",";
			}
			logger.info("http://check.wanfangdata.com.cn/CheckDisplayInfo.ashx?jsonpcallback=jsonp"+new Date().getTime()+"&Id="+paras1.substring(0, paras1.lastIndexOf(","))+"&Memo="+paras2.substring(0,paras2.lastIndexOf(",")));
			//提交一次请求，刷新href连接
			getMethod=new GetMethod("http://check.wanfangdata.com.cn/CheckDisplayInfo.ashx?jsonpcallback=jsonp"+new Date().getTime()+"&Id="+paras1.substring(0, paras1.lastIndexOf(","))+"&Memo="+paras2.substring(0,paras2.lastIndexOf(",")));
			getMethod.setRequestHeader("Accept","image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			getMethod.setRequestHeader("Accept-Language", "zh-cn");
			getMethod.setRequestHeader("Connection", "Keep-Alive");
			getMethod.setRequestHeader("Host", "check.wanfangdata.com.cn");
			getMethod.setRequestHeader("Referer", "http://tran.wanfangdata.com.cn/MyTransferOutList.aspx");
			getMethod.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
			getMethod.setRequestHeader("Cookie", sb2.toString());
			int code3 = httpClient.executeMethod(getMethod);
			logger.info("刷新url连接请求返回状态码："+code3);
			//返回下载地址
			logger.info("刷新url连接请求返回内容："+getMethod.getResponseBodyAsString());
//			以上返回的jsonp字符串："jsonp1356705874281([{\"Id\":\"href_0\",\"Title\":\"论文检测报告[请24小时内下载]\",\"Link\":\"http://check.wanfangdata.com.cn/DetectReport.aspx?id=copydetect_e3b6fcb6-1ea4-4522-89e2-1af4ae54d98c\",\"Description\":\"检测范围：中国学术期刊数据库（CSPD）、中国学位论文全文数据库（CDDB）、中国学术会议论文数据库（CCPD）、中国学术网页数据库（CSWD）\"},{\"Id\":\"href_1\",\"Title\":\"论文检测报告[请24小时内下载]\",\"Link\":\"#\",\"Description\":\"检测范围：中国学术期刊数据库（CSPD）、中国学位论文全文数据库（CDDB）、中国学术会议论文数据库（CCPD）、中国学术网页数据库（CSWD）\"},{\"Id\":\"href_2\",\"Title\":\"论文检测报告[请24小时内下载]\",\"Link\":\"#\",\"Description\":\"检测范围：中国学术期刊数据库（CSPD）、中国学位论文全文数据库（CDDB）、中国学术会议论文数据库（CCPD）、中国学术网页数据库（CSWD）\"}])";
			//重新拼接返回的json字符串
			String jsonStr="{\"test\":"+getMethod.getResponseBodyAsString().substring(getMethod.getResponseBodyAsString().indexOf("(")+1, getMethod.getResponseBodyAsString().lastIndexOf(")"))+"}";
			List<String> hrefs=new ArrayList<String>();
			JSONObject jb = JSONObject.fromObject(jsonStr);   
			JSONArray array=jb.getJSONArray("test"); 
			for(int i=0;i<array.size();i++){
				JSONObject jbb=(JSONObject)array.get(i);
				String href=(String)jbb.get("Link");
				if(href.indexOf("DetectReport.aspx?id=")>-1){
					hrefs.add(href);
					reportNames.add(href.substring(href.lastIndexOf("=")+1));
				}
			}
			//TODO 从以上返回数据中，取出link地址，下载内容
			for(String e:hrefs){
				logger.info(e.substring(e.lastIndexOf("=")+1));
				String tempFile=applicationContext.getResource("downloadTemp").getFile().getAbsolutePath();
				String filename=new File(tempFile).getAbsolutePath()+"/"+e.substring(e.lastIndexOf("=")+1)+".zip";
				File file=new File(filename);
				if(!file.exists()){
					logger.info("------------------下载报告部分-------------------------");
					getMethod=new GetMethod(e);
					getMethod.setRequestHeader("Accept","image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
					getMethod.setRequestHeader("Accept-Language", "zh-cn");
					getMethod.setRequestHeader("Connection", "Keep-Alive");
					getMethod.setRequestHeader("Host", "tran.wanfangdata.com.cn");
					getMethod.setRequestHeader("Referer", "http://www.wanfangdata.com.cn/UserService.aspx");
					getMethod.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
					getMethod.setRequestHeader("Cookie", sb2.toString());
					httpClient.executeMethod(getMethod);
					InputStream is=getMethod.getResponseBodyAsStream();
					FileOutputStream fileout = new FileOutputStream(file);
					byte[] buffer = new byte[1024];
					int ch = 0;
					while ((ch = is.read(buffer)) != -1) {
						fileout.write(buffer, 0, ch);
					}
					is.close();
					fileout.flush();
					fileout.close(); 
					//释放连接
					getMethod.releaseConnection();
				}
			}
		}else{
			//释放第二次连接
			method.releaseConnection();
			viewReport();
		}
		return reportNames;
	}
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		applicationContext=null;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		WFUtil.applicationContext=applicationContext;
	}
}
