package com.gamewolf.database.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;




public class JSONUtil {
	
	public static String getJson(Object object){
		
		 String jsonData = null;
	        try {
	            //使用XContentBuilder创建json数据
	            XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
	            jsonBuild.startObject();
	            Map<String,Object> map=PropertyUtils.describe(object);
	            Iterator<String> it=map.keySet().iterator();
	            while(it.hasNext()){
	            	String fieldName=it.next();
	            	Object fieldValue=map.get(fieldName);
	            	if(fieldValue instanceof Date){
	            		System.out.println("here");
	            	}
	            	jsonBuild.field(fieldName, fieldValue);
	            }
	            jsonBuild.endObject();
	            jsonData = jsonBuild.string();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return jsonData;

	}
	
	

}

