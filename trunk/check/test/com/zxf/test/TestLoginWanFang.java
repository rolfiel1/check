package com.zxf.test;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class TestLoginWanFang {

	public void check() throws HttpException, IOException {
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
		System.out.println("第一次请求返回状态码："+code);
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
		System.out.println("第二次连接用cookie=="+sb.toString());
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
				new NameValuePair("userid", "zlking001"),
				new NameValuePair("password","haiqing"),
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
		System.out.println("第2次登录返回状态码："+method.getStatusCode());
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
		System.out.println("第三次用的cookie"+sb2.toString());
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
		System.out.println("第三次请求返回状态"+code2);
		//得到提交内容页面
		System.out.println("第三次请求返回内容"+getMethod.getResponseBodyAsString());
		
		//TODO 提交测试数据
		
		
	}
	
	public static List<String> viewReport() throws HttpException, IOException{
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
		System.out.println("第一次请求返回状态码："+code);
		//获得页面内容
		System.out.println("第一次请求返回页面代码"+getMethod.getResponseBodyAsString());
		//获得页面cookie
		Header header = getMethod.getResponseHeader("Set-cookie");
		System.out.println(header.getValue());
		//获得转码后的cookie
		System.out.println(URLDecoder.decode(header.getValue()));
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
		System.out.println("拼接的cookie=="+sb.toString());
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
				new NameValuePair("userid", "zlking001"),
				new NameValuePair("password","haiqing"),
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
		System.out.println("第2次登录返回状态码："+method.getStatusCode());
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
		getMethod=new GetMethod("http://www.wanfangdata.com.cn/UserService.aspx");
		getMethod.setRequestHeader("Accept","image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		getMethod.setRequestHeader("Accept-Language", "zh-cn");
		getMethod.setRequestHeader("Connection", "Keep-Alive");
		getMethod.setRequestHeader("Host", "www.wanfangdata.com.cn");
		getMethod.setRequestHeader("Referer", "http://login.wanfangdata.com.cn/Login.aspx?ReturnUrl=http%3a%2f%2fwww.wanfangdata.com.cn%2fUserService.aspx&needLoginAccountType=Person");
		getMethod.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
		getMethod.setRequestHeader("Cookie", sb2.toString());
		int code2 = httpClient.executeMethod(getMethod);
		System.out.println("第三次请求返回状态"+code2);
		//得到报告列表页面
		System.out.println("第三次请求返回报告列表页面"+getMethod.getResponseBodyAsString());
		
		//TODO 下载报告
		
		
		return null;
	}

	public static void main(String args[]) {
		TestLoginWanFang _10086 = new TestLoginWanFang();
		try {
			_10086.viewReport();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}