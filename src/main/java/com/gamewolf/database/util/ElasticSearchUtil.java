package com.gamewolf.database.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * ElasticSearch 工具类
 * @author ZhouChangjin
 *
 */
public class ElasticSearchUtil {
	
	//public static SimpleDateFormat ISO8601DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	
	//public static SimpleDateFormat defaultFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * ES标准返回日期为iso8601格式字符串，返回java中Date类型
	 * @param iso8601String
	 * @return
	 */
	public static Date parseISO8601DateString(String iso8601String){
		SimpleDateFormat ISO8601DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		try {
			return ISO8601DATEFORMAT.parse(iso8601String);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			return null;
			//e.printStackTrace();
		}
	}
	
	public static Date parseDateString(String dateString){
		SimpleDateFormat defaultFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return defaultFormat.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static String convertToISO8601String(Date d){
		SimpleDateFormat ISO8601DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		return ISO8601DATEFORMAT.format(d);
	}
	
	public static String covertDefaultFormat(Date d){
		SimpleDateFormat defaultFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return defaultFormat.format(d);
	}
	
	public static void main(String args[]){
		System.out.println(convertToISO8601String(new Date()));
	}
	

}
