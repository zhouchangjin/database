package com.gamewolf.database.dbmeta.es;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ElasticDatabaseMetaDataBuilder {
	
	
	Map<String,Object> map;
	ESMetaData metadata;
	public ElasticDatabaseMetaDataBuilder prepareBuilder(){
		map=new HashMap<String, Object>();
		metadata=new ESMetaData();
		map.put("properties", metadata.getMap());
		return this;
	}
	
	public <T> ElasticDatabaseMetaDataBuilder buildTypesFromTypeByDefaultSetting(Class<T> clazz){
		Method[] method=clazz.getMethods();
		for(int i=0;i<method.length;i++){
			Method methd=method[i];
			String methodName=methd.getName();
			if(methodName.contains("get")){
				String property=methodName.substring(3,4).toLowerCase()+methodName.substring(4);
				String typeName=methd.getReturnType().getSimpleName();
				if(typeName.equals("String")){
					ESFieldSetting fieldSetting=new ESFieldSetting();
					fieldSetting.setType(ESFieldType.String);
					fieldSetting.setSearchAnalyzer(ESAnalyzerType.IK);
					fieldSetting.setAnalyzer(ESAnalyzerType.IK);
					metadata.addFieldSetting(property, fieldSetting);
				}else if(typeName.equals("Integer")){
					ESFieldSetting fieldSetting=new ESFieldSetting();
					fieldSetting.setType(ESFieldType.Integer);
					metadata.addFieldSetting(property, fieldSetting);
				}else if(typeName.equals("Double")){
					ESFieldSetting fieldSetting=new ESFieldSetting();
					fieldSetting.setType(ESFieldType.Double);
					metadata.addFieldSetting(property, fieldSetting);
				}else if(typeName.equals("Date")){
					ESFieldSetting fieldSetting=new ESFieldSetting();
					fieldSetting.setType(ESFieldType.Date);
					metadata.addFieldSetting(property, fieldSetting);
				}else if(typeName.equals("Float")){
					ESFieldSetting fieldSetting=new ESFieldSetting();
					fieldSetting.setType(ESFieldType.Float);
					metadata.addFieldSetting(property, fieldSetting);
				}else if(typeName.equals("Long")){
					ESFieldSetting fieldSetting=new ESFieldSetting();
					fieldSetting.setType(ESFieldType.Long);
					metadata.addFieldSetting(property, fieldSetting);
				}else if(typeName.equals("Byte")){
					ESFieldSetting fieldSetting=new ESFieldSetting();
					fieldSetting.setType(ESFieldType.Integer);
					metadata.addFieldSetting(property, fieldSetting);
				}else{
					System.out.println("error types"+typeName);
				}
			}


		}
		return this;
	}
	
	public Map<String,Object> get(){
		return map;
	}

}
