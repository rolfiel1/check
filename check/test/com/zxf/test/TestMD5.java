package com.zxf.test;

import java.net.URLDecoder;
import java.util.Date;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.httpclient.NameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import com.check.util.Getpic;
import com.check.util.MD5Util;
import com.check.util.PPUtil;

public class TestMD5 {

	@Test
	public void testCheckImg() {
//		System.out.println(MD5Util.toMD5("admin"));
//		System.out.println(Math.ceil(2727/Double.parseDouble("1000")));
//		System.out.println(URLDecoder.decode("/Transaction.aspx?webTransactionRequest=%7b%22DisplayInfo%22%3a%22%e4%bd%bf%e7%94%a8%e5%8a%9f%e8%83%bd%ef%bc%9a%e8%ae%ba%e6%96%87%e7%9b%b8%e4%bc%bc%e6%80%a7%e6%a3%80%e6%b5%8b%e6%9c%8d%e5%8a%a1%26lt%3bbr+%5c%2f%26gt%3b%e6%a3%80%e6%b5%8b%e8%8c%83%e5%9b%b4%ef%bc%9a%e4%b8%ad%e5%9b%bd%e5%ad%a6%e6%9c%af%e6%9c%9f%e5%88%8a%e6%95%b0%e6%8d%ae%e5%ba%93%ef%bc%88CSPD%ef%bc%89%e3%80%81%e4%b8%ad%e5%9b%bd%e5%ad%a6%e4%bd%8d%e8%ae%ba%e6%96%87%e5%85%a8%e6%96%87%e6%95%b0%e6%8d%ae%e5%ba%93%ef%bc%88CDDB%ef%bc%89%e3%80%81%e4%b8%ad%e5%9b%bd%e5%ad%a6%e6%9c%af%e4%bc%9a%e8%ae%ae%e8%ae%ba%e6%96%87%e6%95%b0%e6%8d%ae%e5%ba%93%ef%bc%88CCPD%ef%bc%89%e3%80%81%e4%b8%ad%e5%9b%bd%e5%ad%a6%e6%9c%af%e7%bd%91%e9%a1%b5%e6%95%b0%e6%8d%ae%e5%ba%93%ef%bc%88CSWD%ef%bc%89%26lt%3bbr+%5c%2f%26gt%3b%e5%be%85%e6%a3%80%e6%b5%8b%e6%96%87%e6%9c%ac%e5%ad%97%e6%95%b0%ef%bc%9a200%e5%ad%97%22%2c%22Request%22%3a%7b%22AuthenticationContext%22%3anull%2c%22ExtraData%22%3a%5b%5d%2c%22ProductDetail%22%3a%22copydetect_3b665066-d25d-4b43-b75b-0e035bc87c05%22%2c%22TransferIn%22%3a%7b%22AccountType%22%3a%22Income%22%2c%22Key%22%3a%22Check%22%7d%2c%22TransferOut%22%3anull%2c%22Turnover%22%3a10.00000%7d%2c%22ReturnUrl%22%3a%22http%3a%5c%2f%5c%2fcheck.wanfangdata.com.cn%5c%2fDetectReport.aspx%3fid%3dcopydetect_3b665066-d25d-4b43-b75b-0e035bc87c05%22%7d"));
//		System.out.println(URLDecoder.decode("/Tran.aspx?transactionRequest=%7B%22AuthenticationContext%22%3Anull%2C%22ExtraData%22%3A%5B%5D%2C%22ProductDetail%22%3A%22copydetect_3b665066-d25d-4b43-b75b-0e035bc87c05%22%2C%22TransferIn%22%3A%7B%22AccountType%22%3A%22Income%22%2C%22Key%22%3A%22Check%22%7D%2C%22TransferOut%22%3A%5B%7B%22AccountType%22%3A%22Person%22%2C%22Key%22%3A%22zlking001%22%7D%5D%2C%22Turnover%22%3A10%7D"));
//	
//		String fiveUrl="/Transaction.aspx?webTransactionRequest=%7b%22DisplayInfo%22%3a%22%e4%bd%bf%e7%94%a8%e5%8a%9f%e8%83%bd%ef%bc%9a%e8%ae%ba%e6%96%87%e7%9b%b8%e4%bc%bc%e6%80%a7%e6%a3%80%e6%b5%8b%e6%9c%8d%e5%8a%a1%26lt%3bbr+%5c%2f%26gt%3b%e6%a3%80%e6%b5%8b%e8%8c%83%e5%9b%b4%ef%bc%9a%e4%b8%ad%e5%9b%bd%e5%ad%a6%e6%9c%af%e6%9c%9f%e5%88%8a%e6%95%b0%e6%8d%ae%e5%ba%93%ef%bc%88CSPD%ef%bc%89%e3%80%81%e4%b8%ad%e5%9b%bd%e5%ad%a6%e4%bd%8d%e8%ae%ba%e6%96%87%e5%85%a8%e6%96%87%e6%95%b0%e6%8d%ae%e5%ba%93%ef%bc%88CDDB%ef%bc%89%e3%80%81%e4%b8%ad%e5%9b%bd%e5%ad%a6%e6%9c%af%e4%bc%9a%e8%ae%ae%e8%ae%ba%e6%96%87%e6%95%b0%e6%8d%ae%e5%ba%93%ef%bc%88CCPD%ef%bc%89%e3%80%81%e4%b8%ad%e5%9b%bd%e5%ad%a6%e6%9c%af%e7%bd%91%e9%a1%b5%e6%95%b0%e6%8d%ae%e5%ba%93%ef%bc%88CSWD%ef%bc%89%26lt%3bbr+%5c%2f%26gt%3b%e5%be%85%e6%a3%80%e6%b5%8b%e6%96%87%e6%9c%ac%e5%ad%97%e6%95%b0%ef%bc%9a200%e5%ad%97%22%2c%22Request%22%3a%7b%22AuthenticationContext%22%3anull%2c%22ExtraData%22%3a%5b%5d%2c%22ProductDetail%22%3a%22copydetect_3b665066-d25d-4b43-b75b-0e035bc87c05%22%2c%22TransferIn%22%3a%7b%22AccountType%22%3a%22Income%22%2c%22Key%22%3a%22Check%22%7d%2c%22TransferOut%22%3anull%2c%22Turnover%22%3a10.00000%7d%2c%22ReturnUrl%22%3a%22http%3a%5c%2f%5c%2fcheck.wanfangdata.com.cn%5c%2fDetectReport.aspx%3fid%3dcopydetect_3b665066-d25d-4b43-b75b-0e035bc87c05%22%7d";
//		String decode5URL=URLDecoder.decode(fiveUrl);
//		String decode5URL_1=decode5URL.substring(decode5URL.indexOf("webTransactionRequest")+"webTransactionRequest".length()+1, decode5URL.indexOf("ReturnUrl")-2);
//		System.out.println(decode5URL_1);
//		String sixURL_request_1=decode5URL_1.substring(decode5URL_1.indexOf("Request")+"Request".length()+2);
//		System.out.println(sixURL_request_1);
//		String sixRUL_request_2=sixURL_request_1.substring(0,sixURL_request_1.indexOf("TransferOut")+"TransferOut".length()+2);
//		System.out.println(sixRUL_request_2);
//		String sixURL_request_3=sixURL_request_1.substring(sixURL_request_1.indexOf("Turnover")-1);
//		System.out.println(sixURL_request_3);
//		String sixURL="/Tran.aspx?transactionRequest="+sixRUL_request_2+"[{\"AccountType\":\"Person\",\"Key\":\"zlking001\"}],"+sixURL_request_3;
//		System.out.println(sixURL);
	
//		System.out.println(PPUtil.getProp("wf.pwd"));
//		System.out.println(PPUtil.getProp("wf.user"));
//		Date date=new Date(1356704494945L);
//		System.out.println(date);
////		
//		String s="jsonp1356705874281([{\"Id\":\"href_0\",\"Title\":\"论文检测报告[请24小时内下载]\",\"Link\":\"http://check.wanfangdata.com.cn/DetectReport.aspx?id=copydetect_e3b6fcb6-1ea4-4522-89e2-1af4ae54d98c\",\"Description\":\"检测范围：中国学术期刊数据库（CSPD）、中国学位论文全文数据库（CDDB）、中国学术会议论文数据库（CCPD）、中国学术网页数据库（CSWD）\"},{\"Id\":\"href_1\",\"Title\":\"论文检测报告[请24小时内下载]\",\"Link\":\"#\",\"Description\":\"检测范围：中国学术期刊数据库（CSPD）、中国学位论文全文数据库（CDDB）、中国学术会议论文数据库（CCPD）、中国学术网页数据库（CSWD）\"},{\"Id\":\"href_2\",\"Title\":\"论文检测报告[请24小时内下载]\",\"Link\":\"#\",\"Description\":\"检测范围：中国学术期刊数据库（CSPD）、中国学位论文全文数据库（CDDB）、中国学术会议论文数据库（CCPD）、中国学术网页数据库（CSWD）\"}])";
//		System.out.println(s.substring(s.indexOf("(")+1, s.lastIndexOf(")")));
//		String s="{\"test\":[{\"Id\":\"href_0\",\"Title\":\"论文检测报告[请24小时内下载]\",\"Link\":\"http://check.wanfangdata.com.cn/DetectReport.aspx?id=copydetect_e3b6fcb6-1ea4-4522-89e2-1af4ae54d98c\",\"Description\":\"检测范围：中国学术期刊数据库（CSPD）、中国学位论文全文数据库（CDDB）、中国学术会议论文数据库（CCPD）、中国学术网页数据库（CSWD）\"},{\"Id\":\"href_1\",\"Title\":\"论文检测报告[请24小时内下载]\",\"Link\":\"#\",\"Description\":\"检测范围：中国学术期刊数据库（CSPD）、中国学位论文全文数据库（CDDB）、中国学术会议论文数据库（CCPD）、中国学术网页数据库（CSWD）\"},{\"Id\":\"href_2\",\"Title\":\"论文检测报告[请24小时内下载]\",\"Link\":\"#\",\"Description\":\"检测范围：中国学术期刊数据库（CSPD）、中国学位论文全文数据库（CDDB）、中国学术会议论文数据库（CCPD）、中国学术网页数据库（CSWD）\"}]}";
//		JSONObject jb = JSONObject.fromObject(s);   
//		JSONArray array=jb.getJSONArray("test"); 
//		JSONObject jbb=(JSONObject)array.get(0);
//		System.out.println(jbb.get("Link"));
//		8fe99dacc1743ec209ef06e2a7c53b93
		System.out.println(MD5Util.toMD5("521ily515"));
		
	}
}
