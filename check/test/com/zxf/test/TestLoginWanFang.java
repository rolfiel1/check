package com.zxf.test;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;

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

	public void test() throws HttpException, IOException {
		
		
		HttpClient httpClient = new HttpClient();
		DefaultHttpParams.getDefaultParams().setParameter( "http.protocol.cookie-policy",CookiePolicy.BROWSER_COMPATIBILITY);
		//第一次连接到页面
		String url = "http://login.wanfangdata.com.cn/Login.aspx?ReturnUrl=http%3a%2f%2fcheck.wanfangdata.com.cn%2fUploadPaper.aspx&needLoginAccountType=Person";
		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Accept","image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		getMethod.setRequestHeader("Accept-Language", "zh-cn");
		getMethod.setRequestHeader("Connection", "Keep-Alive");
		getMethod.setRequestHeader("Host", "login.wanfangdata.com.cn");
		getMethod.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
		int code = httpClient.executeMethod(getMethod);
		//返回页面状态代码
		System.out.println("第一次登录返回状态码："+code);
		//获得页面内容
//		System.out.println(getMethod.getResponseBodyAsString());
		
		//获得页面cookie
		Header header = getMethod.getResponseHeader("Set-cookie");
		System.out.println(header.getValue());
		//获得转码后的cookie
		System.out.println(URLDecoder.decode(header.getValue()));
		
		
		//获得sessionId
		String headerCookie = header.getValue();
		String SessionIds = headerCookie.substring(headerCookie.indexOf("NET_SessionId=")+ "NET_SessionId=".length());
		String sessionId=SessionIds.substring(0,SessionIds.indexOf(";"));
//		System.out.println(sessionId);

		String cookie1=header.getValue().substring(0,header.getValue().indexOf("domain"));
//		System.out.println(cookie1);
		
		//拼接第二次连接用cookie
		Date date1=new Date();
		StringBuffer sb = new StringBuffer();
        sb.append(cookie1);  
		sb.append("Hm_lvt_f5e6bd27352a71a202024e821056162b="+date1.getTime()+";");
		sb.append("Hm_lpvt_f5e6bd27352a71a202024e821056162b="+date1.getTime()+";");
		sb.append("ASP.NET_SessionId=").append(sessionId);
		//cookie内容
		System.out.println("拼接的cookie=="+sb.toString());
		System.out.println(URLDecoder.decode(sb.toString()));
		//拼接第三次用cookie
//WFKS.Auth=%7b%22Context%22%3a%7b%22AccountIds%22%3a%5b%22Person.zlking001%22%5d%2c%22Data%22%3a%5b%7b%22Key%22%3a%22Person.zlking001.DisplayName%22%2c%22Value%22%3anull%7d%5d%2c%22Roles%22%3a%5b%5d%2c%22SessionId%22%3a%222746828e-d9ec-4e01-b708-0b11c0e39ff7%22%2c%22Sign%22%3a%22ddNAiNAuMRHRp%2b4DlXgUKpnOkCM4dXmQ7CLPROp%2byKVO5QQobyGHZ5mn6V8DelLr%22%7d%2c%22LastUpdate%22%3a%222012-11-20T00%3a54%3a45Z%22%2c%22TicketSign%22%3a%22kEXyOOF4E%2bbBSq5xkcswrg%3d%3d%22%7d;Hm_lvt_f5e6bd27352a71a202024e821056162b=1353295195437,1353371975062,1353372599609,1353372793140;  Hm_lpvt_f5e6bd27352a71a202024e821056162b=1353372793140
        
		String s1="";
		Document doc = Jsoup.parse(getMethod.getResponseBodyAsString());
		//__VIEWSTATE
		String __VIEWSTATE=doc.getElementById("__VIEWSTATE").val();
//		System.out.println(__VIEWSTATE);
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
		for(Header h:headers){
			System.out.println(h.getName()+"=="+h.getValue());
		}
		//第三次连接用的cookie
		String header3=headers[headers.length-1].getValue();
		String cookie3=header3.substring(0, header3.indexOf("domain"));
		
		StringBuffer sb2 = new StringBuffer();
		sb2.append(cookie3);
		sb2.append("Hm_lvt_f5e6bd27352a71a202024e821056162b="+date1.getTime()+";");
		sb2.append("Hm_lpvt_f5e6bd27352a71a202024e821056162b="+date1.getTime()+";");
		sb2.append("ASP.NET_SessionId=").append(sessionId);
		//第三次用的cookie
		System.out.println(sb2.toString());
		//释放第二次连接
		method.releaseConnection();
		
		 
		//第三次连接，跳转到页面/UploadPaper.aspx
		getMethod=new GetMethod("http://check.wanfangdata.com.cn/UploadPaper.aspx");
		getMethod.setRequestHeader("Accept","image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		getMethod.setRequestHeader("Accept-Language", "zh-cn");
		getMethod.setRequestHeader("Connection", "Keep-Alive");
		getMethod.setRequestHeader("Host", "check.wanfangdata.com.cn");
		getMethod.setRequestHeader("Referer", "http://login.wanfangdata.com.cn/Login.aspx?ReturnUrl=http%3a%2f%2fcheck.wanfangdata.com.cn%2fUploadPaper.aspx&needLoginAccountType=Person");
		getMethod.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
		getMethod.setRequestHeader("Cookie", sb2.toString());
		int code2 = httpClient.executeMethod(getMethod);
		System.out.println(code2);
		//得到提交内容页面
		System.out.println(getMethod.getResponseBodyAsString());
	}

	public static void main(String args[]) {
		TestLoginWanFang _10086 = new TestLoginWanFang();
		try {
			_10086.test();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}