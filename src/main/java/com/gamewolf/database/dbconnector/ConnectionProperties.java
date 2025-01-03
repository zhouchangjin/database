package com.gamewolf.database.dbconnector;

import java.util.HashMap;
import java.util.Map;

import com.gamewolf.database.util.PropertiesUtil;

public class ConnectionProperties {
	public HashMap<String,String> connectionProperties;
	public ConnectionProperties(){
		connectionProperties=new HashMap<String, String>();
	}
	public void putProperty(String pName,String pValue){
		connectionProperties.put(pName, pValue);
	}
	public String getProperty(String pName){
		return connectionProperties.get(pName);
	}
	
	public void setHashMap(HashMap<String,String> map){
		connectionProperties.putAll(map);
	}
	
	public boolean contains(String property) {
		return connectionProperties.containsKey(property);
	}
	
	public void setMap(Map map){
		connectionProperties.putAll(map);
	}
	
	public static ConnectionProperties loadPropertiesFromPropetiesFile(String filePath,String fileName,boolean isResource) {
		ConnectionProperties connectionProperties=new ConnectionProperties();
		HashMap<String,String> proMap=PropertiesUtil.parsePropertyFile(fileName, filePath, !isResource);
		connectionProperties.setHashMap(proMap);
		return connectionProperties;
	}

}
