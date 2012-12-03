package com.zxf.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.check.util.Getpic;
import com.check.util.OCR;


public class TestLoginPaperpass {

	public void test() throws HttpException, IOException {
		
		HttpClient httpClient = new HttpClient();
		DefaultHttpParams.getDefaultParams().setParameter("http.protocol.cookie-policy",CookiePolicy.BROWSER_COMPATIBILITY);
		//---------第一次登录页面-----------------------------
		String url = "http://www.paperpass.org/login.aspx";
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8"); 
		getMethod.setRequestHeader("Accept","image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		getMethod.setRequestHeader("Accept-Language", "zh-cn");
		getMethod.setRequestHeader("Accept-Encoding", "gzip, deflate");
		getMethod.setRequestHeader("Connection", "Keep-Alive");
		getMethod.setRequestHeader("Cache-Control", "private");
		getMethod.setRequestHeader("Host", "www.paperpass.org");
		getMethod.setRequestHeader("Referer", "http://www.paperpass.org/");
		getMethod.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
		int code = httpClient.executeMethod(getMethod);
		//返回页面状态代码
		System.out.println("返回第一次登录状态码："+code);
		//获得页面cookie
		Header header = getMethod.getResponseHeader("Set-cookie");
//		System.out.println(header.getValue());
		String headerCookie = header.getValue();
		String SessionId = headerCookie.substring(headerCookie.indexOf("NET_SessionId=")+ "NET_SessionId=".length(), headerCookie.indexOf(";"));
//		System.out.println(SessionId);
		
		//-------------拼接后面用的cookie--------------
		Date date1=new Date();
		StringBuffer sb = new StringBuffer();
        sb.append("SESSION_COOKIE=11; CNZZDATA2347458=cnzz_eid=32045686-1352955131-&ntime=1352955131&cnzz_a=0&retime="+date1.getTime()+"&sin=&ltime="+date1.getTime()+"&rtime=0;");  
		sb.append("ASP.NET_SessionId=").append(SessionId).append(";");
        sb.append("pgv_pvi=8799122432");
        
		//-------------输出第一次登录得到的页面内容---------------
//		System.out.println(getMethod.getResponseBodyAsString());
        
        
        //--------------获得页面验证码------------------
		Document doc = Jsoup.parse(getMethod.getResponseBodyAsString());
		
		Elements gif = doc.select("img[src$=16]");
		System.out.println(gif.attr("src"));
		
		//获得验证码
		String validCode=null;
		try {
			validCode=OCR.recognizeText(new File(Getpic.saveUrlAs("http://www.paperpass.org"+gif.attr("src"), "WebRoot/temp")), "gif");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("验证码："+validCode);
		
		//--------------获得页面隐藏域内容------------------
		//__VIEWSTATE
		String __VIEWSTATE=doc.getElementById("__VIEWSTATE").val();
		//__EVENTVALIDATION
		String __EVENTVALIDATION=doc.getElementById("__EVENTVALIDATION").val();
//		System.out.println(__VIEWSTATE);
//		System.out.println(__EVENTVALIDATION);
        //-------------释放第一次连接------------
        getMethod.releaseConnection();
		
		//---------登录---------第二次连接--------------------------
		httpClient.getHostConfiguration().setHost("www.paperpass.org", 80, "http");
		PostMethod secondPost=new PostMethod("/login.aspx");
		secondPost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8"); 
		NameValuePair[] nvp2 = { new NameValuePair("__EVENTTARGET", ""),
				new NameValuePair("__EVENTARGUMENT", ""),
				new NameValuePair("__VIEWSTATE",__VIEWSTATE),
				new NameValuePair("__EVENTVALIDATION",__EVENTVALIDATION),
				new NameValuePair("TextBox_UserName", "zlking"),
				new NameValuePair("TextBox_UserPass", "haiqing"),
				new NameValuePair("TextBox_YanZheng", validCode),
				new NameValuePair("login_button", "%E7%99%BB%E5%BD%95")
				};
		secondPost.setRequestBody(nvp2);
		secondPost.setRequestHeader("Accept","image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		secondPost.setRequestHeader("Accept-Language", "zh-cn");
		secondPost.setRequestHeader("Referer","http://www.paperpass.org/login.aspx");
		secondPost.setRequestHeader("Accept-Encoding", "gzip, deflate");
		secondPost.setRequestHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
		secondPost.setRequestHeader("Host", "www.paperpass.org");
		secondPost.setRequestHeader("Connection","Keep-Alive");
		secondPost.setRequestHeader("Cookie", sb.toString());
		httpClient.executeMethod(secondPost);
		
		System.out.println("第二次连接返回代码："+secondPost.getStatusCode());
		
		//获得返回头信息中跳转地址
		Header header3=secondPost.getResponseHeader("Location");
		//释放第二次连接
		secondPost.releaseConnection();
		if(302==secondPost.getStatusCode()&&"/user_upload_txt.aspx".equals(header3.getValue())){
////			返回成功代码302 跳转页面/user_upload_txt.aspx
//			getMethod=new GetMethod("http://www.paperpass.org/user_upload_txt.aspx");
//			getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
//			getMethod.setRequestHeader("Accept","image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
//			getMethod.setRequestHeader("Accept-Language", "zh-cn");
//			getMethod.setRequestHeader("Accept-Encoding", "gzip, deflate");
//			getMethod.setRequestHeader("Connection", "Keep-Alive");
//			getMethod.setRequestHeader("Cache-Control", "private");
//			getMethod.setRequestHeader("Host", "www.paperpass.org");
//			getMethod.setRequestHeader("Referer", "http://www.paperpass.org/login.aspx");
//			getMethod.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
//			getMethod.setRequestHeader("Cookie", sb.toString());
//			int code2 = httpClient.executeMethod(getMethod);
//			System.out.println("提交页面返回状态码："+code2);
////			//------------------------得到提交内容页面----------------------
////			System.out.println(getMethod.getResponseBodyAsString());
//			
//			Document doc2 = Jsoup.parse(getMethod.getResponseBodyAsString());
//			String __VIEWSTATE2=doc2.getElementById("__VIEWSTATE").val();
//			//__EVENTVALIDATION
//			String __EVENTVALIDATION2=doc2.getElementById("__EVENTVALIDATION").val();
//			
//			//释放连接
//			getMethod.releaseConnection();
//
//			//------------------------提交检测内容---------------------------
//			httpClient.getHostConfiguration().setHost("www.paperpass.org", 80, "http");
//			PostMethod thirdPost=new PostMethod("/user_upload_txt.aspx");
//			thirdPost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8"); 
//			String ccc="%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E";
//			NameValuePair[] nvp3 = { new NameValuePair("__EVENTTARGET", "Submit"),
//					new NameValuePair("__EVENTARGUMENT", ""),
//					new NameValuePair("__VIEWSTATE",__VIEWSTATE2),
//					new NameValuePair("__EVENTVALIDATION",__EVENTVALIDATION2),
//					new NameValuePair("paper_title", "网上商城"),
//					new NameValuePair("paper_author", "赵晓飞"),
//					new NameValuePair("paper_content", URLDecoder.decode(ccc))
//					};
//			thirdPost.setRequestBody(nvp3);
//			thirdPost.setRequestHeader("Accept","image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
//			thirdPost.setRequestHeader("Accept-Language", "zh-cn");
//			thirdPost.setRequestHeader("Referer","http://www.paperpass.org/user_upload_txt.aspx");
//			thirdPost.setRequestHeader("Accept-Encoding", "gzip, deflate");
//			thirdPost.setRequestHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
//			thirdPost.setRequestHeader("Host", "www.paperpass.org");
//			thirdPost.setRequestHeader("Connection","Keep-Alive");
//			thirdPost.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
//			thirdPost.setRequestHeader("Cookie", sb.toString());
//			httpClient.executeMethod(thirdPost);
//			System.out.println("提交数据返回状态码："+thirdPost.getStatusCode());
////			//返回确认提交页面
//			System.out.println(thirdPost.getResponseBodyAsString());
//			Document doc3 = Jsoup.parse(thirdPost.getResponseBodyAsString());
//			String __VIEWSTATE3=doc3.getElementById("__VIEWSTATE").val();
//			//__EVENTVALIDATION
//			String __EVENTVALIDATION3=doc3.getElementById("__EVENTVALIDATION").val();
//			
//			thirdPost.releaseConnection();
////			System.out.println(__VIEWSTATE3);
////			System.out.println(__EVENTVALIDATION3);
//			//-------------确认提交------------------------------------
//			httpClient.getHostConfiguration().setHost("www.paperpass.org", 80, "http");
//			PostMethod forthPost=new PostMethod("/user_upload_receive.aspx");
//			forthPost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8"); 
//			NameValuePair[] nvp4 = { 
//					new NameValuePair("__VIEWSTATE",__VIEWSTATE3),
//					new NameValuePair("__EVENTVALIDATION",__EVENTVALIDATION3)
//					};
//			forthPost.setRequestBody(nvp4);
//			forthPost.setRequestHeader("Accept","image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
//			forthPost.setRequestHeader("Accept-Language", "zh-cn");
//			forthPost.setRequestHeader("Referer","http://www.paperpass.org/user_upload_txt.aspx");
//			forthPost.setRequestHeader("Accept-Encoding", "gzip, deflate");
//			forthPost.setRequestHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
//			forthPost.setRequestHeader("Host", "www.paperpass.org");
//			forthPost.setRequestHeader("Connection","Keep-Alive");
//			forthPost.setRequestHeader("Cookie", sb.toString());
//			httpClient.executeMethod(forthPost);
//			System.out.println("确认提交返回状态码："+forthPost.getStatusCode());
////			System.out.println(forthPost.getResponseBodyAsString());
//			//获得返回头信息中跳转地址
//			Header header4=forthPost.getResponseHeader("Location");
//			if(302==forthPost.getStatusCode()&&"/user_upload_ok.aspx?target=txt".equals(header4.getValue())){
//				System.out.println("确认提交论文成功!");
//			}
			
			//------------------------查看报告--------------------------
			getMethod=new GetMethod("http://www.paperpass.org/user_view_report.aspx");
			getMethod.setRequestHeader("Accept","image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			getMethod.setRequestHeader("Accept-Language", "zh-cn");
			getMethod.setRequestHeader("Accept-Encoding", "gzip, deflate");
			getMethod.setRequestHeader("Connection", "Keep-Alive");
			getMethod.setRequestHeader("Host", "www.paperpass.org");
			getMethod.setRequestHeader("Referer", "http://www.paperpass.org/user_upload_ok.aspx?target=txt");
			getMethod.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
			getMethod.setRequestHeader("Cookie", sb.toString());
			int code3 = httpClient.executeMethod(getMethod);
			System.out.println("查看报告页面状态码："+code3);
			//得到报告页面内容
			System.out.println(getMethod.getResponseBodyAsString());
			
			//获得页面中所有报告的连接地址
			Document docViewReport = Jsoup.parse(getMethod.getResponseBodyAsString());
			Elements links = docViewReport.select("a[href*=user_down_report.aspx?&filename=]");
			//释放连接
			getMethod.releaseConnection();			
			//-----------------下载检测报告-------------------------------
				for(Element e:links){
					System.out.println(e.attr("href"));
					System.out.println(e.attr("href").substring(e.attr("href").lastIndexOf("=")+1));
					String filename=new File("WebRoot/temp").getAbsolutePath()+"/"+e.attr("href").substring(e.attr("href").lastIndexOf("=")+1)+".zip";
					//------------------下载报告部分-------------------------
					getMethod=new GetMethod("http://www.paperpass.org/"+e.attr("href"));
					getMethod.setRequestHeader("Accept","image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
					getMethod.setRequestHeader("Accept-Language", "zh-cn");
					getMethod.setRequestHeader("Accept-Encoding", "gzip, deflate");
					getMethod.setRequestHeader("Connection", "Keep-Alive");
					getMethod.setRequestHeader("Host", "www.paperpass.org");
					getMethod.setRequestHeader("Referer", "http://www.paperpass.org/user_view_report.aspx");
					getMethod.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
					getMethod.setRequestHeader("Cookie", sb.toString());
					httpClient.executeMethod(getMethod);
					InputStream is=getMethod.getResponseBodyAsStream();
					File file=new File(filename);
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

			
			
		}else{
			//登录失败返回页面重新登录
			test();
		}
		
	}

	public static void main(String args[]) {
		TestLoginPaperpass _10086 = new TestLoginPaperpass();
		try {
			_10086.test();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}