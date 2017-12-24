package com.gamewolf.database.dbconnector;

import java.util.HashMap;
import java.util.Map;

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
	
	public void setMap(Map map){
		connectionProperties.putAll(map);
	}

}
