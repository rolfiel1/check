package com.check.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class PPUtil implements ApplicationContextAware, DisposableBean {

	private static Logger logger = Logger.getLogger(PPUtil.class);

	private static ApplicationContext applicationContext = null;

	private static String ppName = getProp("pp.user");

	private static String ppPwd = getProp("pp.pwd");

	public static List<String> check(String title, String author, String content)
			throws HttpException, IOException {
		List<String> reportNames = new ArrayList<String>();
		logger.info("ppName:" + ppName);
		logger.info("ppPwd:" + ppPwd);
		String tempFile = applicationContext.getResource("downloadTemp")
				.getFile().getAbsolutePath();
		logger.info(tempFile);
		HttpClient httpClient = new HttpClient();
		DefaultHttpParams.getDefaultParams().setParameter(
				"http.protocol.cookie-policy",
				CookiePolicy.BROWSER_COMPATIBILITY);
		// ---------第一次登录页面-----------------------------
		String url = "http://www.paperpass.org/login.aspx";
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		getMethod
				.setRequestHeader(
						"Accept",
						"image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		getMethod.setRequestHeader("Accept-Language", "zh-cn");
		getMethod.setRequestHeader("Accept-Encoding", "gzip, deflate");
		getMethod.setRequestHeader("Connection", "Keep-Alive");
		getMethod.setRequestHeader("Cache-Control", "private");
		getMethod.setRequestHeader("Host", "www.paperpass.org");
		getMethod.setRequestHeader("Referer", "http://www.paperpass.org/");
		getMethod
				.setRequestHeader(
						"User-Agent",
						"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
		int code = httpClient.executeMethod(getMethod);
		// 返回页面状态代码
		logger.info("返回第一次登录状态码：" + code);
		// 获得页面cookie
		Header header = getMethod.getResponseHeader("Set-cookie");
		String headerCookie = header.getValue();
		String SessionId = headerCookie.substring(
				headerCookie.indexOf("NET_SessionId=")
						+ "NET_SessionId=".length(), headerCookie.indexOf(";"));

		// -------------拼接后面用的cookie--------------
		Date date1 = new Date();
		StringBuffer sb = new StringBuffer();
		sb.append("SESSION_COOKIE=11; CNZZDATA2347458=cnzz_eid=32045686-1352955131-&ntime=1352955131&cnzz_a=0&retime="
				+ date1.getTime()
				+ "&sin=&ltime="
				+ date1.getTime()
				+ "&rtime=0;");
		sb.append("ASP.NET_SessionId=").append(SessionId).append(";");
		sb.append("pgv_pvi=8799122432");

		// -------------输出第一次登录得到的页面内容---------------
		// logger.info(getMethod.getResponseBodyAsString());

		// --------------获得页面验证码------------------
		Document doc = Jsoup.parse(getMethod.getResponseBodyAsString());
		Elements gif = doc.select("img[src$=16]");

		// 获得验证码
		String validCode = null;
		try {
			String validCodePath = Getpic.saveUrlAs("http://www.paperpass.org"
					+ gif.attr("src"), tempFile);
			logger.info(validCodePath);
			File ocrFile = new File(validCodePath);
			validCode = OCR.recognizeText(ocrFile, "gif");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("验证码：" + validCode);
		// --------------获得页面隐藏域内容------------------
		// __VIEWSTATE
		String __VIEWSTATE = doc.getElementById("__VIEWSTATE").val();
		// __EVENTVALIDATION
		String __EVENTVALIDATION = doc.getElementById("__EVENTVALIDATION")
				.val();
		// -------------释放第一次连接------------
		getMethod.releaseConnection();

		// ---------登录---------第二次连接--------------------------
		httpClient.getHostConfiguration().setHost("www.paperpass.org", 80,
				"http");
		PostMethod secondPost = new PostMethod("/login.aspx");
		secondPost.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		NameValuePair[] nvp2 = { new NameValuePair("__EVENTTARGET", ""),
				new NameValuePair("__EVENTARGUMENT", ""),
				new NameValuePair("__VIEWSTATE", __VIEWSTATE),
				new NameValuePair("__EVENTVALIDATION", __EVENTVALIDATION),
				new NameValuePair("TextBox_UserName", ppName),
				new NameValuePair("TextBox_UserPass", ppPwd),
				new NameValuePair("TextBox_YanZheng", validCode),
				new NameValuePair("login_button", "%E7%99%BB%E5%BD%95") };
		secondPost.setRequestBody(nvp2);
		secondPost
				.setRequestHeader(
						"Accept",
						"image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		secondPost.setRequestHeader("Accept-Language", "zh-cn");
		secondPost.setRequestHeader("Referer",
				"http://www.paperpass.org/login.aspx");
		secondPost.setRequestHeader("Accept-Encoding", "gzip, deflate");
		secondPost
				.setRequestHeader(
						"User-Agent",
						"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
		secondPost.setRequestHeader("Host", "www.paperpass.org");
		secondPost.setRequestHeader("Connection", "Keep-Alive");
		secondPost.setRequestHeader("Cookie", sb.toString());
		httpClient.executeMethod(secondPost);

		logger.info("第二次连接返回代码：" + secondPost.getStatusCode());

		// 获得返回头信息中跳转地址
		Header header3 = secondPost.getResponseHeader("Location");
		// 释放第二次连接
		secondPost.releaseConnection();
		if (302 == secondPost.getStatusCode()
				&& "/user_upload_txt.aspx".equals(header3.getValue())) {
			// 返回成功代码302 跳转页面/user_upload_txt.aspx
			getMethod = new GetMethod(
					"http://www.paperpass.org/user_upload_txt.aspx");
			getMethod.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			getMethod
					.setRequestHeader(
							"Accept",
							"image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			getMethod.setRequestHeader("Accept-Language", "zh-cn");
			getMethod.setRequestHeader("Accept-Encoding", "gzip, deflate");
			getMethod.setRequestHeader("Connection", "Keep-Alive");
			getMethod.setRequestHeader("Cache-Control", "private");
			getMethod.setRequestHeader("Host", "www.paperpass.org");
			getMethod.setRequestHeader("Referer",
					"http://www.paperpass.org/login.aspx");
			getMethod
					.setRequestHeader(
							"User-Agent",
							"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
			getMethod.setRequestHeader("Cookie", sb.toString());
			int code2 = httpClient.executeMethod(getMethod);
			logger.info("提交页面返回状态码：" + code2);
			// //------------------------得到提交内容页面----------------------
			// logger.info(getMethod.getResponseBodyAsString());

			Document doc2 = Jsoup.parse(getMethod.getResponseBodyAsString());
			String __VIEWSTATE2 = doc2.getElementById("__VIEWSTATE").val();
			// __EVENTVALIDATION
			String __EVENTVALIDATION2 = doc2
					.getElementById("__EVENTVALIDATION").val();

			// 释放连接
			getMethod.releaseConnection();

			// ------------------------提交检测内容---------------------------
			httpClient.getHostConfiguration().setHost("www.paperpass.org", 80,
					"http");
			PostMethod thirdPost = new PostMethod("/user_upload_txt.aspx");
			thirdPost.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			NameValuePair[] nvp3 = {
					new NameValuePair("__EVENTTARGET", "Submit"),
					new NameValuePair("__EVENTARGUMENT", ""),
					new NameValuePair("__VIEWSTATE", __VIEWSTATE2),
					new NameValuePair("__EVENTVALIDATION", __EVENTVALIDATION2),
					new NameValuePair("paper_title", title),
					new NameValuePair("paper_author", author),
					new NameValuePair("paper_content", content) };
			thirdPost.setRequestBody(nvp3);
			thirdPost
					.setRequestHeader(
							"Accept",
							"image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			thirdPost.setRequestHeader("Accept-Language", "zh-cn");
			thirdPost.setRequestHeader("Referer",
					"http://www.paperpass.org/user_upload_txt.aspx");
			thirdPost.setRequestHeader("Accept-Encoding", "gzip, deflate");
			thirdPost
					.setRequestHeader(
							"User-Agent",
							"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
			thirdPost.setRequestHeader("Host", "www.paperpass.org");
			thirdPost.setRequestHeader("Connection", "Keep-Alive");
			thirdPost.setRequestHeader("Cookie", sb.toString());
			httpClient.executeMethod(thirdPost);
			logger.info("提交数据返回状态码：" + thirdPost.getStatusCode());
			// //返回确认提交页面
			logger.info(thirdPost.getResponseBodyAsString());
			Document doc3 = Jsoup.parse(thirdPost.getResponseBodyAsString());
			String __VIEWSTATE3 = doc3.getElementById("__VIEWSTATE").val();
			// __EVENTVALIDATION
			String __EVENTVALIDATION3 = doc3
					.getElementById("__EVENTVALIDATION").val();
			thirdPost.releaseConnection();
			// -------------确认提交------------------------------------
			httpClient.getHostConfiguration().setHost("www.paperpass.org", 80,
					"http");
			PostMethod forthPost = new PostMethod("/user_upload_receive.aspx");
			forthPost.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			NameValuePair[] nvp4 = {
					new NameValuePair("__VIEWSTATE", __VIEWSTATE3),
					new NameValuePair("__EVENTVALIDATION", __EVENTVALIDATION3) };
			forthPost.setRequestBody(nvp4);
			forthPost
					.setRequestHeader(
							"Accept",
							"image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			forthPost.setRequestHeader("Accept-Language", "zh-cn");
			forthPost.setRequestHeader("Referer",
					"http://www.paperpass.org/user_upload_txt.aspx");
			forthPost.setRequestHeader("Accept-Encoding", "gzip, deflate");
			forthPost
					.setRequestHeader(
							"User-Agent",
							"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
			forthPost.setRequestHeader("Host", "www.paperpass.org");
			forthPost.setRequestHeader("Connection", "Keep-Alive");
			forthPost.setRequestHeader("Cookie", sb.toString());
			httpClient.executeMethod(forthPost);
			logger.info("确认提交返回状态码：" + forthPost.getStatusCode());
			// 获得返回头信息中跳转地址
			Header header4 = forthPost.getResponseHeader("Location");
			if (302 == forthPost.getStatusCode()
					&& "/user_upload_ok.aspx?target=txt".equals(header4
							.getValue())) {
				logger.info("确认提交论文成功!");
				// ------------------------查看报告--------------------------
				getMethod = new GetMethod(
						"http://www.paperpass.org/user_view_report.aspx");
				getMethod
						.setRequestHeader(
								"Accept",
								"image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
				getMethod.setRequestHeader("Accept-Language", "zh-cn");
				getMethod.setRequestHeader("Accept-Encoding", "gzip, deflate");
				getMethod.setRequestHeader("Connection", "Keep-Alive");
				getMethod.setRequestHeader("Host", "www.paperpass.org");
				getMethod
						.setRequestHeader("Referer",
								"http://www.paperpass.org/user_upload_ok.aspx?target=txt");
				getMethod
						.setRequestHeader(
								"User-Agent",
								"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
				getMethod.setRequestHeader("Cookie", sb.toString());
				int code3 = httpClient.executeMethod(getMethod);
				logger.info("查看报告页面状态码：" + code3);
				// 得到报告页面内容
				logger.info(getMethod.getResponseBodyAsString());
				// 获得页面中所有报告的连接地址
				Document docViewReport = Jsoup.parse(getMethod
						.getResponseBodyAsString());
				// 得到报告名称
				Elements names = docViewReport
						.select("a[href*=user_down_original.aspx?&filename=]");
				for (Element entity : names) {
					System.out.println(entity.attr("href").substring(
							entity.attr("href").lastIndexOf("=") + 1));
					reportNames.add(entity.attr("href").substring(
							entity.attr("href").lastIndexOf("=") + 1));
				}
				// 释放连接
				getMethod.releaseConnection();
			}
		} else {
			// 登录失败返回页面重新登录
			check(title, author, content);
		}
		return reportNames;
	}

	// 下载检测报告
	public static List<String> viewReport() throws HttpException, IOException {
		List<String> reportNames = new ArrayList<String>();
		logger.info("ppName:" + ppName);
		logger.info("ppPwd:" + ppPwd);
		String tempFile = applicationContext.getResource("downloadTemp")
				.getFile().getAbsolutePath();
		logger.info("tempFile：" + tempFile);
		HttpClient httpClient = new HttpClient();
		DefaultHttpParams.getDefaultParams().setParameter(
				"http.protocol.cookie-policy",
				CookiePolicy.BROWSER_COMPATIBILITY);
		// ---------第一次登录页面-----------------------------
		String url = "http://www.paperpass.org/login.aspx";
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		getMethod
				.setRequestHeader(
						"Accept",
						"image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		getMethod.setRequestHeader("Accept-Language", "zh-cn");
		getMethod.setRequestHeader("Accept-Encoding", "gzip, deflate");
		getMethod.setRequestHeader("Connection", "Keep-Alive");
		getMethod.setRequestHeader("Cache-Control", "private");
		getMethod.setRequestHeader("Host", "www.paperpass.org");
		getMethod.setRequestHeader("Referer", "http://www.paperpass.org/");
		getMethod
				.setRequestHeader(
						"User-Agent",
						"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
		int code = httpClient.executeMethod(getMethod);
		// 返回页面状态代码
		logger.info("返回第一次登录状态码：" + code);
		// 获得页面cookie
		Header header = getMethod.getResponseHeader("Set-cookie");
		String headerCookie = header.getValue();
		String SessionId = headerCookie.substring(
				headerCookie.indexOf("NET_SessionId=")
						+ "NET_SessionId=".length(), headerCookie.indexOf(";"));

		// -------------拼接后面用的cookie--------------
		Date date1 = new Date();
		StringBuffer sb = new StringBuffer();
		sb.append("SESSION_COOKIE=11; CNZZDATA2347458=cnzz_eid=32045686-1352955131-&ntime=1352955131&cnzz_a=0&retime="
				+ date1.getTime()
				+ "&sin=&ltime="
				+ date1.getTime()
				+ "&rtime=0;");
		sb.append("ASP.NET_SessionId=").append(SessionId).append(";");
		sb.append("pgv_pvi=8799122432");

		// -------------输出第一次登录得到的页面内容---------------
		// logger.info(getMethod.getResponseBodyAsString());

		// --------------获得页面验证码------------------
		Document doc = Jsoup.parse(getMethod.getResponseBodyAsString());

		Elements gif = doc.select("img[src$=16]");

		// 获得验证码
		String validCode = null;
		try {
			String validCodePath = Getpic.saveUrlAs("http://www.paperpass.org"
					+ gif.attr("src"), tempFile);
			logger.info("validCodePath：" + validCodePath);
			File ocrFile = new File(validCodePath);
			validCode = OCR.recognizeText(ocrFile, "gif");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("验证码：" + validCode);

		// --------------获得页面隐藏域内容------------------
		// __VIEWSTATE
		String __VIEWSTATE = doc.getElementById("__VIEWSTATE").val();
		// __EVENTVALIDATION
		String __EVENTVALIDATION = doc.getElementById("__EVENTVALIDATION")
				.val();
		// -------------释放第一次连接------------
		getMethod.releaseConnection();

		// ---------登录---------第二次连接--------------------------
		httpClient.getHostConfiguration().setHost("www.paperpass.org", 80,
				"http");
		PostMethod secondPost = new PostMethod("/login.aspx");
		secondPost.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		NameValuePair[] nvp2 = { new NameValuePair("__EVENTTARGET", ""),
				new NameValuePair("__EVENTARGUMENT", ""),
				new NameValuePair("__VIEWSTATE", __VIEWSTATE),
				new NameValuePair("__EVENTVALIDATION", __EVENTVALIDATION),
				new NameValuePair("TextBox_UserName", ppName),
				new NameValuePair("TextBox_UserPass", ppPwd),
				new NameValuePair("TextBox_YanZheng", validCode),
				new NameValuePair("login_button", "%E7%99%BB%E5%BD%95") };
		secondPost.setRequestBody(nvp2);
		secondPost
				.setRequestHeader(
						"Accept",
						"image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		secondPost.setRequestHeader("Accept-Language", "zh-cn");
		secondPost.setRequestHeader("Referer",
				"http://www.paperpass.org/login.aspx");
		secondPost.setRequestHeader("Accept-Encoding", "gzip, deflate");
		secondPost
				.setRequestHeader(
						"User-Agent",
						"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
		secondPost.setRequestHeader("Host", "www.paperpass.org");
		secondPost.setRequestHeader("Connection", "Keep-Alive");
		secondPost.setRequestHeader("Cookie", sb.toString());
		httpClient.executeMethod(secondPost);

		logger.info("第二次连接返回代码：" + secondPost.getStatusCode());

		// 获得返回头信息中跳转地址
		Header header3 = secondPost.getResponseHeader("Location");
		// 释放第二次连接
		secondPost.releaseConnection();
		if (302 == secondPost.getStatusCode()
				&& "/user_upload_txt.aspx".equals(header3.getValue())) {
			// ------------------------查看报告--------------------------
			getMethod = new GetMethod(
					"http://www.paperpass.org/user_view_report.aspx");
			getMethod
					.setRequestHeader(
							"Accept",
							"image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			getMethod.setRequestHeader("Accept-Language", "zh-cn");
			getMethod.setRequestHeader("Accept-Encoding", "gzip, deflate");
			getMethod.setRequestHeader("Connection", "Keep-Alive");
			getMethod.setRequestHeader("Host", "www.paperpass.org");
			getMethod.setRequestHeader("Referer",
					"http://www.paperpass.org/user_upload_ok.aspx?target=txt");
			getMethod
					.setRequestHeader(
							"User-Agent",
							"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
			getMethod.setRequestHeader("Cookie", sb.toString());
			int code3 = httpClient.executeMethod(getMethod);
			logger.info("查看报告页面状态码：" + code3);
			// 得到报告页面内容
			logger.info(getMethod.getResponseBodyAsString());
			// 获得页面中所有报告的连接地址
			Document docViewReport = Jsoup.parse(getMethod
					.getResponseBodyAsString());
			Elements links = docViewReport
					.select("a[href*=user_down_report.aspx?&filename=]");
			// 释放连接
			getMethod.releaseConnection();
			// -----------------下载检测报告-------------------------------
			for (Element e : links) {
				logger.info(e.attr("href"));
				logger.info(e.attr("href").substring(
						e.attr("href").lastIndexOf("=") + 1));
				String filename = new File(tempFile).getAbsolutePath()
						+ "/"
						+ e.attr("href").substring(
								e.attr("href").lastIndexOf("=") + 1) + ".zip";
				reportNames.add(e.attr("href").substring(
						e.attr("href").lastIndexOf("=") + 1));
				File file = new File(filename);
				if (!file.exists()) {
					logger.info("------------------下载报告部分-------------------------");
					getMethod = new GetMethod("http://www.paperpass.org/"
							+ e.attr("href"));
					getMethod
							.setRequestHeader(
									"Accept",
									"image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
					getMethod.setRequestHeader("Accept-Language", "zh-cn");
					getMethod.setRequestHeader("Accept-Encoding",
							"gzip, deflate");
					getMethod.setRequestHeader("Connection", "Keep-Alive");
					getMethod.setRequestHeader("Host", "www.paperpass.org");
					getMethod.setRequestHeader("Referer",
							"http://www.paperpass.org/user_view_report.aspx");
					getMethod
							.setRequestHeader(
									"User-Agent",
									"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
					getMethod.setRequestHeader("Cookie", sb.toString());
					httpClient.executeMethod(getMethod);
					InputStream is = getMethod.getResponseBodyAsStream();
					FileOutputStream fileout = new FileOutputStream(file);
					byte[] buffer = new byte[1024];
					int ch = 0;
					while ((ch = is.read(buffer)) != -1) {
						fileout.write(buffer, 0, ch);
					}
					is.close();
					fileout.flush();
					fileout.close();
					// 释放连接
					getMethod.releaseConnection();
				}
			}
		} else {
			// 登录失败返回页面重新登录
			viewReport();
		}
		return reportNames;
	}

	public static String getProp(String prop) {
		ResourceBundle rb = ResourceBundle.getBundle("account", Locale.CHINESE);
		return rb.getString(prop);
	}

	// public static void main(String[] args) throws HttpException, IOException
	// {
	// // check("","","");
	// viewReport();
	// }

	public static String readValue(String filePath, String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			props.load(in);
			String value = props.getProperty(key);
			System.out.println(key + value);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 读取properties的全部信息
	public static void readProperties(String filePath) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			props.load(in);
			Enumeration en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = props.getProperty(key);
				System.out.println(key + Property);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 写入properties信息
	public static void writeProperties(String filePath, String parameterName,
			String parameterValue) {
		Properties prop = new Properties();
		try {
			InputStream fis = new FileInputStream(filePath);
			// 从输入流中读取属性列表（键和元素对）
			prop.load(fis);
			// 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			OutputStream fos = new FileOutputStream(filePath);
			prop.setProperty(parameterName, parameterValue);
			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			prop.store(fos, "Update '" + parameterName + "' value");
		} catch (IOException e) {
			System.err.println("Visit " + filePath + " for updating "
					+ parameterName + " value error");
		}
	}

	public static void main(String[] args) {
		readValue(
				"E:\\server\\apache-tomcat-6.0.33\\apache-tomcat-6.0.33\\webapps\\check\\WEB-INF\\classes\\account.properties",
				"app_key");
		writeProperties(
				"E:\\server\\apache-tomcat-6.0.33\\apache-tomcat-6.0.33\\webapps\\check\\WEB-INF\\classes\\account.properties",
				"app_key", "21353710");
		// readProperties("info.properties" );
		// System.out.println("OK");
	}

	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		applicationContext = null;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		PPUtil.applicationContext = applicationContext;
	}
}
