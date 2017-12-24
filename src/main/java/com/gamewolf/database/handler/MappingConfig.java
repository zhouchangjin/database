package com.gamewolf.database.handler;

import java.util.HashMap;

public class MappingConfig {
	
	Class mappingClazz;
	HashMap<String,String> attributeMapping;
	public MappingConfig(){
		attributeMapping=new HashMap<String, String>();
	}
	
	public void putMapping(String colSql,String attribute){
		attributeMapping.put(colSql, attribute);
	}
	
	boolean contains(String colSql){
		return attributeMapping.containsKey(colSql);
	}
	
	public String getMapping(String colSql){
		String colNames[]=colSql.split("_");
		if(contains(colSql)){
			return attributeMapping.get(colSql);
		}else if(colNames.length>1){
			String name="";
			for(int i=0;i<colNames.length;i++){
				if(i==0){
					name+=colNames[i].toLowerCase();
				}else{
					name+=colNames[i].substring(0,1).toUpperCase()+colNames[i].toLowerCase().substring(1);
				}
				
			}
			attributeMapping.put(colSql, name);
			return name;
		}else{
			attributeMapping.put(colSql, colSql.toLowerCase());
			return colSql;
		}
		
	}

	public Class getMappingClazz() {
		return mappingClazz;
	}

	public void setMappingClazz(Class mappingClazz) {
		this.mappingClazz = mappingClazz;
	}

}
