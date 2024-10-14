/**
 * 2016-4-25 MysqlRowCallBackHandler.java
 * com.harmonywisdom.yuqing.analyzer.handler
 *  
 */
package com.gamewolf.database.handler;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.alibaba.fastjson.JSONObject;

/**
 * @author zhouchangjin
 *
 */
public class DefaultSqlRowCallBackHandler implements RowCallbackHandler {
	
	IRowRetrieveCallback callback;
	
	MappingConfig config;
	
	public int rowCount=0;

	public MappingConfig getConfig() {
		return config;
	}

	public void setConfig(MappingConfig config) {
		this.config = config;
	}

	public IRowRetrieveCallback getCallback() {
		return callback;
	}

	public void setCallback(IRowRetrieveCallback callback) {
		this.callback = callback;
	}
	
	public void setProperty(Object mappedObj,String attribute,Object value)
			throws IllegalAccessException, InvocationTargetException {
		if(mappedObj instanceof JSONObject) {
			JSONObject object=(JSONObject) mappedObj;
			object.put(attribute, value);
		}else if(mappedObj instanceof Map) {
		     Map<String,Object> mapObj=(Map<String,Object>)mappedObj;
		     mapObj.put(attribute, value);
		}else {
			BeanUtils.setProperty(mappedObj, attribute, value);
		}
	}

	@Override
	public void processRow(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		ResultSetMetaData metadata=rs.getMetaData();
		rowCount++;
		Object obj;
		try {
			obj = config.getMappingClazz().newInstance();
			for(int i=1;i<=metadata.getColumnCount();i++){
				String colSql=metadata.getColumnName(i);
				String mappedAttr=config.getMapping(colSql);
				if(rs.getObject(i)!=null) {
					setProperty(obj, mappedAttr, rs.getObject(i));
					//BeanUtils.setProperty(obj, mappedAttr, rs.getObject(i));
				}
			}
			if(callback!=null){
				callback.onEachRow(obj,rowCount);
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
