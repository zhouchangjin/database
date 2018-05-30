package com.gamewolf.database.dbmeta.es;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class ESMetaData {
	
	Map<String,ESFieldSetting> map;
	
	public ESMetaData() {
		map=new HashMap<String,ESFieldSetting>();
	}
	
	public Map<String,ESFieldSetting> getMap(){
		return map;
	}
	
	
	
	public ESMetaData addFieldSetting(String fieldName,ESFieldSetting setting){
		map.put(fieldName, setting);
		return this;
	}
	
	public String toString(){
		return JSON.toJSONString(map);
	}
	
	public static void main(String args[]){
		ESMetaData e=new ESMetaData();
		ESFieldSetting set=new ESFieldSetting();
		set.setType(ESFieldType.Double);
		set.setSearchAnalyzer(ESAnalyzerType.IK);
		e.addFieldSetting("test", set);
		System.out.println(e);
		System.out.println(ESFieldType.Double);
	}
	

}
