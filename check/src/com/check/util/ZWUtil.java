package com.check.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
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

import com.check.bean.Report;

public class ZWUtil implements ApplicationContextAware, DisposableBean {

	private static Logger logger = Logger.getLogger(ZWUtil.class);

	private static ApplicationContext applicationContext = null;

	private static String zwName = PPUtil.getProp("zw.user");

	private static String zwPwd = PPUtil.getProp("zw.pwd");

	private static HttpClient httpClient = null;

	public static String getStringNeed(String str, String split, int i) {
		String strs[] = str.split(split);
		return strs[i];
	}

	public static Map<String, String> loginSucHandle() throws IOException {
		Map<String, String> ret = new HashMap<String, String>();
		httpClient = new HttpClient();
		DefaultHttpParams.getDefaultParams().setParameter(
				"http.protocol.cookie-policy",
				CookiePolicy.BROWSER_COMPATIBILITY);
		String __LASTFOCUS = null;
		String __EVENTTARGET = null;
		String __EVENTARGUMENT = null;
		String __VIEWSTATE = null;
		String __EVENTVALIDATION = null;
		// =============表单域============================
		// 此处破解验证码成功率不高
		String url = "http://check.cnki.net/vip/";
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		// ======消息头设置===============
		getMethod
				.setRequestHeader(
						"Accept",
						"image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		getMethod.setRequestHeader("Referer", "http://check.cnki.net/vip/");
		getMethod.setRequestHeader("Accept-Language", "zh-cn");
		getMethod
				.setRequestHeader(
						"User-Agent",
						"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
		getMethod.setRequestHeader("Accept-Encoding", "gzip, deflate");
		getMethod.setRequestHeader("Host", "check.cnki.net");
		getMethod.setRequestHeader("Connection", "Keep-Alive");
		// ====================第一次访问============================
		int code = httpClient.executeMethod(getMethod);
		// 返回页面状态代码
		logger.info("访问--状态码：" + code);
		// 返回页面代码
//		logger.info("访问--页面：" + getMethod.getResponseBodyAsString());
		// 获得页面cookie
		Header header = getMethod.getResponseHeader("Set-cookie");
		String cookieB  = header.getValue()
//				.substring(0, header.getValue().indexOf(";"))
				;
		// 返回cookieB
		ret.put("cookieB", cookieB);
		// ========构建第一次登陆页面doc===========
		Document doc = Jsoup.parse(getMethod.getResponseBodyAsString());

		Elements gif = doc.select("[alt=验证码]");

		String validCode = null;
		String picPath = HttpParams.url + gif.attr("src");
		 String tempFile =applicationContext.getResource("downloadTemp").getFile().getAbsolutePath();
		// 临时测试路径
//		String tempFile = "D://";
		File file = new File(Getpic.saveUrlAs(picPath, tempFile));
		try {
			validCode = OCR.recognizeText2(file, "gif");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("验证码:" + validCode);
		if (!validCode.matches("[\\da-zA-Z]{5}")) {
			getMethod.releaseConnection();
			return loginSucHandle();
		}
		// ===隐藏表单域获取========
		__LASTFOCUS = doc.getElementById("__LASTFOCUS").val();
		__EVENTTARGET = doc.getElementById("__EVENTTARGET").val();
		__EVENTARGUMENT = doc.getElementById("__EVENTARGUMENT").val();
		__VIEWSTATE = doc.getElementById("__VIEWSTATE").val();
		__EVENTVALIDATION = doc.getElementById("__EVENTVALIDATION").val();
		// =========释放连接================
		getMethod.releaseConnection();

		// ===================第二次访问,登录=================================
		httpClient.getHostConfiguration().setHost("check.cnki.net", 80, "http");
		PostMethod pm = new PostMethod("/vip/login.aspx");
		pm.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				"utf-8");
		NameValuePair nvp[] = new NameValuePair[] {
				new NameValuePair("__LASTFOCUS", __LASTFOCUS),
				new NameValuePair("__EVENTTARGET", __EVENTTARGET),
				new NameValuePair("__EVENTARGUMENT", __EVENTARGUMENT),
				new NameValuePair("__VIEWSTATE", __VIEWSTATE),
				new NameValuePair("__EVENTVALIDATION", __EVENTVALIDATION),
				new NameValuePair("VIP_UserName", zwName),
				new NameValuePair("VIP_UserPwd", zwPwd),
				new NameValuePair("VIP_Check", validCode),// 验证码
				new NameValuePair("ImageButton_VIP.x", "40"),
				new NameValuePair("ImageButton_VIP.y", "16"), };
		pm.setRequestBody(nvp);
		pm.setRequestHeader("Host", "check.cnki.net");
		pm.setRequestHeader(
				"User-Agent",
				"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
		pm.setRequestHeader(
				"Accept",
				"image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		pm.setRequestHeader("Accept-Language", "zh-cn");
		pm.setRequestHeader("Accept-Encoding", "gzip, deflate");
		pm.setRequestHeader("Referer", "http://check.cnki.net/vip/login.aspx");
		pm.setRequestHeader("Cookie", cookieB);
		pm.setRequestHeader("Connection", "Keep-Alive");
		pm.setRequestHeader("Cache-Control", "no-cache");
		pm.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

		// =========登录=============
		int code2 = httpClient.executeMethod(pm);
		logger.info("登录--状态码：" + code2);
		Header [] head=pm.getResponseHeaders();
		for(Header h:head){
			System.out.println(h.getName()+"=="+h.getValue());
		}
		String docStr = null;
		if (code2 == 302) {
			// ============转向================
			pm.releaseConnection();
			GetMethod gm = new GetMethod("http://check.cnki.net/vip/SimResult.aspx?ID=482");
			gm.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
			gm.setRequestHeader("Host", "check.cnki.net");
			gm.setRequestHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
			gm.setRequestHeader("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			gm.setRequestHeader("Accept-Language","zh-cn");
			gm.setRequestHeader("Accept-Encoding","gzip, deflate");
			gm.setRequestHeader("Referer", "http://check.cnki.net/vip/");
			gm.setRequestHeader("Cookie", cookieB);
			gm.setRequestHeader("Connection", "Keep-Alive");
			gm.setRequestHeader("Cache-Control", "no-cache");
			int status3 = httpClient.executeMethod(gm);
			logger.info("登录后转向--状态码：" + status3);
			Header[] hs=gm.getResponseHeaders();
			for(Header h:hs){
				System.out.println(h.getName()+"--"+h.getValue());
			}
			// 睡眠200毫秒供转向
			try {
				TimeUnit.MILLISECONDS.sleep(200);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			docStr = gm.getResponseBodyAsString();
			gm.releaseConnection();
			System.out.println(docStr);
			System.out.println(docStr.contains("wxbd1226"));
			ret.put("docStr", docStr);
			return ret;
		} else {
			return loginSucHandle();
		}
	}

	public static String check(String title, String author, String content)
			throws HttpException, IOException {
		httpClient = new HttpClient();
		DefaultHttpParams.getDefaultParams().setParameter(
				"http.protocol.cookie-policy",
				CookiePolicy.BROWSER_COMPATIBILITY);
		Map<String, String> paramsList = loginSucHandle();
		String cookieB = paramsList.get("cookieB");
		String docStr = paramsList.get("docStr");
		// =============上传文章使用手工录入================
		Document docArt = Jsoup.parse(docStr);
		// ===隐藏参数===
		String __VIEWSTATE = docArt.getElementById("__VIEWSTATE").val();
		String __EVENTVALIDATION = docArt.getElementById("__EVENTVALIDATION")
				.val();
		// =================提交检测======================
		httpClient.getHostConfiguration().setHost("check.cnki.net", 80, "http");
		PostMethod pm = new PostMethod("/vip/SimResult.aspx?ID=482");
		pm.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				"utf-8");
		pm.setRequestHeader("Accept", "*/*");
		pm.setRequestHeader("Accept-Language", "zh-cn");
		pm.setRequestHeader("Referer","http://check.cnki.net/vip/SimResult.aspx?ID=482#");
		pm.setRequestHeader("x-microsoftajax", "Delta=true");
		pm.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
		pm.setRequestHeader("Cache-Control", "no-cache");
		pm.setRequestHeader("Accept-Encoding", "gzip, deflate");
		pm.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET4.0C; .NET4.0E)");
		pm.setRequestHeader("Host", "check.cnki.net");// 服务器必须返回一个刷新后的文档
		pm.setRequestHeader("Cookie", cookieB.substring(0, cookieB.indexOf(";"))+"; SR_FileNameS=cnki%3A");
		pm.setRequestHeader("Connection", "Keep-Alive");
		// ====================postdata消息体参数=========================
		NameValuePair nvp2[] = new NameValuePair[] {
				new NameValuePair("ScriptManager1","UpdatePanel1%7CLinkButton2"),
				new NameValuePair("__EVENTTARGET","LinkButton2"),
				new NameValuePair("__EVENTARGUMENT",""),
				new NameValuePair("__LASTFOCUS",""),
				new NameValuePair("__VIEWSTATE",__VIEWSTATE),
				new NameValuePair("__VIEWSTATEENCRYPTED",""),
				new NameValuePair("__EVENTVALIDATION",__EVENTVALIDATION),
				new NameValuePair("DDL1","%E7%AF%87%E5%90%8D"),
				new NameValuePair("TB1",""),
				new NameValuePair("DDLJCZT","0"),
				new NameValuePair("ddlFolderlist","482"),
				new NameValuePair("TextBox1",""),
				new NameValuePair("TextBox5",""),
				new NameValuePair("TextBox6",""),
				new NameValuePair("TextBox9",""),
				new NameValuePair("TextBox7",""),
				new NameValuePair("TextBox2",""),
				new NameValuePair("TextBox3",""),
				new NameValuePair("TextBox11",""),
				new NameValuePair("TextBox4",content),
				new NameValuePair("TextBox10",""),
				new NameValuePair("TextBox8",""),
				new NameValuePair("tbUnit",""),
				new NameValuePair("RadioButtonList1","2"),
				new NameValuePair("Hidden_Type",""),
				new NameValuePair("tbFolderName",""),
				new NameValuePair("tbFolderDes",""),
				new NameValuePair("cbf2%240","on"),
				new NameValuePair("cbf2%241","on"),
				new NameValuePair("cbf2%242","on"),
				new NameValuePair("cbf2%243","on"),
				new NameValuePair("cbf2%244","on"),
				new NameValuePair("cbf2%245","on"),
				new NameValuePair("cbf2%246","on"),
				new NameValuePair("cbf2%247","on"),
				new NameValuePair("cbf2%248","on"),
				new NameValuePair("cbf2%249","on"),
				new NameValuePair("cbf2%2410","on"),
				new NameValuePair("Text_time1","1900-1-1"),
				new NameValuePair("Text_time2","2099-12-31"),
				new NameValuePair("__ASYNCPOST","true")
		};
		pm.setRequestBody(nvp2);
		// =========上传提交检测=============
		int code4 = httpClient.executeMethod(pm);
		logger.info("提交检测--状态码：" + code4);
		System.out.println(pm.getResponseBodyAsString());

		return null;
	}

	// 下载检测报告
	public static List<String> viewReport(List<Report> list)
			throws HttpException, IOException {
		httpClient = new HttpClient();
		DefaultHttpParams.getDefaultParams().setParameter(
				"http.protocol.cookie-policy",
				CookiePolicy.BROWSER_COMPATIBILITY);
		List<String> reportNames = new ArrayList<String>();
		Map<String, String> paramsList = loginSucHandle();
		String cookieB = (String) paramsList.get(0);
		// ==============解析登陆页下载报告====================
		for (Report rs : list) {
			String ppid = rs.getPpid();
			// 报告页面链接，访问之后保存该页面
			String url5 = HttpParams.url5 + "?LeftFile=" + ppid + "&t=p";
			GetMethod gm = new GetMethod(url5);
			HttpMethodParams hp = gm.getParams();
			hp.setParameter("Host", HttpParams.header_Host2);
			hp.setParameter("User-Agent", HttpParams.header_User_Agent2);
			hp.setParameter("Accept", HttpParams.header_Accept2);
			hp.setParameter("Accept-Language",
					HttpParams.header_Accept_Encoding2);
			hp.setParameter("Accept-Encoding",
					HttpParams.header_Accept_Encoding2);
			hp.setParameter("Referer", HttpParams.header_Referer4);
			hp.setParameter("Cookie", cookieB);
			hp.setParameter("Connection", HttpParams.header_Connection2);
			int status3 = 0;
			try {
				status3 = httpClient.executeMethod(gm);
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			logger.info("下载报告--状态码：" + status3);
			String res = gm.getResponseBodyAsString();
			gm.releaseConnection();
			String dateStr = DateStrUtil.date2str2(new Date());
			String filePath = wFile(res, dateStr + ".html", 2);
			if (filePath == null) {
				return reportNames;
			}
			reportNames.add(ppid + "#" + filePath);
		}
		httpClient = null;
		return reportNames;
	}

	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		applicationContext = null;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		ZWUtil.applicationContext = applicationContext;
	}

	private static String wFile(String result, String fileName, int i) throws IOException {
		FileChannel fc = null;
		String path = null;

		System.out.println(System.getProperty("user.dir"));
		if (i == 1) {
			path = System.getProperty("user.dir") + "/WebRoot/temp/";
		} else if (i == 2) {
			// path = System.getProperty("user.dir") + "/WebRoot/downloadTemp/";
			path =applicationContext.getResource("downloadTemp").getFile().getAbsolutePath()+"/downloadTemp";
			
		} else if (i == 3) {
			path = System.getProperty("user.dir") + "/WebRoot/WEB-INF/report/";
		}
		File file = new File(path + fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				file = null;
			}
		}
		if (file == null) {
			return null;
		}
		try {
			fc = new FileOutputStream(file).getChannel();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// String s = new String("如果有汉字呢？？？,good morning,good,good a");
		ByteBuffer bb = ByteBuffer.allocate(result.length() * 2);
		bb.put(result.getBytes());
		bb.flip();
		try {
			fc.write(bb);
			fc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return path + fileName;
		return fileName;
	}

	public static String rTXT(String pathFile, int i) {
		BufferedReader in = null;
		StringBuffer sb = new StringBuffer();
		String line = null;
		String path = null;
		if (i == 1) {
			path = System.getProperty("user.dir") + "/WebRoot/temp/";
		} else {
			path = System.getProperty("user.dir") + "/WebRoot/downloadTemp/";
		}
		try {
			in = new BufferedReader(new FileReader(path + pathFile));
			while ((line = in.readLine()) != null) {
				sb.append(line.trim());
			}
		} catch (IOException e) {
			System.out.println("query:err");
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) throws IOException {

		 Map map=loginSucHandle();
//		 System.out.println(map.size());
//		check("",
//				"",
//				"我这是一个测试啊啊我个测试啊啊我个测试啊啊我个测试啊啊我个测试啊啊我个测试啊啊我个测试啊啊我个测试啊啊我个测试啊啊我个测试啊啊我个测试啊啊我个测试啊啊我个测试啊啊我个测试啊啊我个测试啊啊我个测试啊啊我个测试啊啊我个测试啊啊我这是一个测试啊啊我这是一个测试啊啊我这是一个测试啊啊我这是一个测试啊啊我这是一个测试啊啊我这是一个测试啊啊我这是一个测试啊啊我这是一个测试啊啊我这是一个测试啊啊我这是一个测试啊啊我这是一个测试啊啊我这是一个测试啊啊我这是一个测试啊啊我这是一个测试啊啊我这是一个测试啊啊我这是一个测试啊啊我这是一个测试啊啊我这是一个测试啊啊我这是一个测试啊啊我这是一个测试啊啊我这是一个测试啊啊我这是一个测试啊啊");

	}

	private static String getNeedString(String val, String split, int i) {
		String va[] = val.trim().split(split);
		return va[i];
	}

}
