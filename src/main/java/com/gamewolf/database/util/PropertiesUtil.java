package com.gamewolf.database.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import org.springframework.core.io.UrlResource;

public class PropertiesUtil {
	
	public static HashMap<String,String> parsePropertyFile(String fileName,String resourcePath,Boolean external){
		HashMap<String,String> map=new HashMap<String,String>();
		Properties pro = new Properties();
		if(!external) {
			InputStream is=PropertiesUtil.class.getResourceAsStream(resourcePath+fileName);
			try {
				pro.load(is);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			try {
				InputStream is=new FileInputStream(new File(resourcePath+fileName));
				pro.load(is);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Iterator<Object> keyIterator=pro.keySet().iterator();
		while(keyIterator.hasNext()) {
			String key=keyIterator.next().toString();
			String value=pro.get(key).toString();
			map.put(key, value);
		}
		return map;
	}
	
	public static void main(String[] args) {
			parsePropertyFile("default.properties", "/", false);
	}

}
