package com.check.util; 

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** 
 * @version 1.0 
 * 
 * @author 作者姓名 
 * 
 * 创建时间：2012-6-7 下午04:30:35 
 * 
 * 类说明 
 */
public class DateStrUtil {
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
	static SimpleDateFormat format4sql = new SimpleDateFormat("yyyy-MM-dd");   
	public static String date2str(Date date){
		if(date!=null){
			return format.format(date); 
		}else{
			return null;
		}
		
	}
	
	public static Date str2date(String str) throws ParseException{
		if(!("".equals(str.trim()))&&str!=null){
			return format.parse(str); 
		}else{
			return null;
		}
		
	}
	
	public static Date str2date4sql(String str) throws ParseException{
		if(!("".equals(str.trim()))&&str!=null){
			return format4sql.parse(str); 
		}else{
			return null;
		}
		
	}
	
	public static String date2str4sql(Date date){
		if(date!=null){
			return format4sql.format(date); 
		}else{
			return null;
		}
		
	}
}
 
