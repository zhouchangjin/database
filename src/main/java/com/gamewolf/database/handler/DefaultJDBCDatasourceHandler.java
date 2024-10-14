package com.gamewolf.database.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.alibaba.fastjson.JSONObject;
import com.gamewolf.database.dbconnector.ConnectionMsg;
import com.gamewolf.database.dbsource.ITableDatasource;
import com.gamewolf.database.orm.annotation.mapping.IgnoreField;

public class DefaultJDBCDatasourceHandler{
	/**
	 * 预览数据数量
	 */
	public static int PREVIEWSIZE=10;
	
	JdbcTemplate template;
	/**
	 * 映射
	 */
	MappingConfig config;
	/**
	 * 默认列串
	 */
	String defaultColStr="";
	/**
	 * 默认列
	 */
	List<String> defaultColList=new ArrayList<String>();
	
	int previewSize=PREVIEWSIZE;
	
	ITableDatasource datasource;
	

	void buildConfig(){
		//如果表里没数据，将会有bug?
		//System.out.println(datasource.testConnection());
		template.query("select * from "+getTable()+" limit 0,1", new RowMapper<Object>(){
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				
					try {
						Object obj=config.getMappingClazz().newInstance();
						ResultSetMetaData metaData=rs.getMetaData();
						for(int i=1;i<=metaData.getColumnCount();i++){
							String colSql=metaData.getColumnName(i);
							config.getMapping(colSql);
						}
						return obj;
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					} 
			}
			
		});
	}
	
	public String getDefaultColumn(){
		if(defaultColStr.equals("")){
			buildColumns();
		}
		return defaultColStr;
	}
	
	public void buildColumns(){
		String cols="";
		Iterator<String> it=config.attributeMapping.keySet().iterator();
		while(it.hasNext()){
			String key=it.next();
			cols+=key+",";
			defaultColList.add(key);
		}
		cols=cols.substring(0,cols.length()-1);
		defaultColStr=cols;
   }
	
	public String buildQuestionMarkStr(int size){
		StringBuilder builder=new StringBuilder();
		for(int i=0;i<size;i++){
			builder.append("?");
			if(i<size-1){
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public String getTable() {
		return this.datasource.getTable();
	}
	

	public int getPreviewSize() {
		return previewSize;
	}


	public void setPreviewSize(int previewSize) {
		this.previewSize = previewSize;
	}
	
	public MappingConfig getConfig() {
		return config;
	}


	public void setConfig(MappingConfig config) {
		this.config = config;
	}
	
	public JdbcTemplate getTemplate() {
		return template;
	}
	
	
	public boolean isConnected() {
		if(datasource.testConnection().getErrorNum().equals(ConnectionMsg.SUCCESS_NUM)){
			return true;
		}else{
			return false;
		}	
	}
	
	
	public List<Object> preview() {
		List<Object> list=template.query("select "+getDefaultColumn()+"  from "+getTable()+" limit 0,"+PREVIEWSIZE, new RowMapper<Object>(){
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				
					try {
						Object obj=config.getMappingClazz().newInstance();
						ResultSetMetaData metaData=rs.getMetaData();
						for(int i=1;i<=metaData.getColumnCount();i++){
							String colSql=metaData.getColumnName(i);
							String mappedAttr=config.getMapping(colSql);
							if(rs.getObject(i)!=null) {
								//BeanUtils.setProperty(obj, mappedAttr, rs.getObject(i));
								if(obj instanceof JSONObject) {
									JSONObject object=(JSONObject) obj;
									object.put(mappedAttr, rs.getObject(i));
								}else if(obj instanceof Map) {
								     Map<String,Object> mapObj=(Map<String,Object>)obj;
								     mapObj.put(mappedAttr, rs.getObject(i));
								}else {
									BeanUtils.setProperty(obj, mappedAttr, rs.getObject(i));
								}
							}
							
						}
						return obj;
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
			}
			
		});
		return list;
	}
	
	public void retrieveRowsWithQueryClause(IRowRetrieveCallback callback,String whereClause,String orderby,String orderbyType,int start,int limit){
		DefaultSqlRowCallBackHandler handler=new DefaultSqlRowCallBackHandler();
		handler.setCallback(callback);
		handler.setConfig(config);
		String sql="select "+getDefaultColumn()+" from "+datasource.getTable();
		if(whereClause!=null && !whereClause.equals("")){
			sql+=" where "+whereClause;
		}
		if(orderby!=null && !orderby.equals("")){
			sql+=" order by "+orderby+" "+orderbyType;
		}
		sql+=" limit "+start+","+limit;
		try{
			template.query(sql,handler );
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public String toColumnCase(String inputName) {
		String memberName=inputName.substring(0, 1).toUpperCase()+inputName.substring(1);
		String reg="[A-Z][a-z]+";
		Pattern p=Pattern.compile(reg);
		Matcher matcher=p.matcher(memberName);
		String item="";
		while(matcher.find()) {
			String text=matcher.group(0).toLowerCase();
			item+=text+"_";
		}
		item=item.substring(0,item.length()-1);
		return item;
	}
	
	public void insertIncomplete(Object t) {
		String colStr="";
		List<String> colList=new ArrayList<String>();
		int size=0;
		if(t instanceof Map) {
			Map<String,Object> map=(Map<String,Object>)t;
			for(String key:map.keySet()) {
				size++;
				String colName=toColumnCase(key);
				colStr+=colName+",";
				colList.add(colName);
			}
		}else if(t instanceof JSONObject) {
			JSONObject obj=(JSONObject)t;
			for(String key:obj.keySet()) {
				size++;
				String colName=toColumnCase(key);
				colStr+=colName+",";
				colList.add(colName);
			}
			
		}else {
			String className=t.getClass().getName();
			Method m[]=t.getClass().getMethods();
			for(int i=0;i<m.length;i++){
				if(m[i].getDeclaringClass().getName().equals(className)) {
					if(m[i].getName().startsWith("get") && m[i].getAnnotation(IgnoreField.class)==null) {
						size++;
						String memberName=m[i].getName().replace("get", "");
						/**
						String reg="[A-Z][a-z]+";
						Pattern p=Pattern.compile(reg);
						Matcher matcher=p.matcher(memberName);
						String item="";
						while(matcher.find()) {
							String text=matcher.group(0).toLowerCase();
							item+=text+"_";
						}
						item=item.substring(0,item.length()-1);**/
						String item=toColumnCase(memberName);
						colStr+=item+",";
						colList.add(item);
					}
				}
				
			}
			
		}
		colStr=colStr.substring(0,colStr.length()-1);
		String sql="insert into "+datasource.getTable()+"("+colStr+") values("+buildQuestionMarkStr(size)+")";
		template.update(sql, new SqlPreparedStatementSetter(t,colList));
		
	}
	
	public void insertObject(Object t){
		String sql="insert into "+datasource.getTable()+"("+getDefaultColumn()+") values("+buildQuestionMarkStr(config.attributeMapping.keySet().size())+")";
		template.update(sql, new SqlPreparedStatementSetter(t,defaultColList));
	}
	
	public void updateObject(String idColumn,Object t){
		String sql="update "+datasource.getTable()+" set ";
		String set="";
		for(int i=0;i<defaultColList.size();i++){
			String col=defaultColList.get(i);
			set+=" "+col+"=?";
			if(i<defaultColList.size()-1){
				set+=",";
			}
		}
		String propertyName=config.getMapping(idColumn);
		String value="";
		if(t instanceof Map) {
			Map<String,Object> map=(Map<String,Object>)t;
			value=map.get(idColumn).toString();
		}else if(t instanceof JSONObject) {
			JSONObject obj=(JSONObject)t;
			value=obj.getString(idColumn);
		}else {
			String getterName="get"+propertyName.substring(0,1).toUpperCase()+propertyName.substring(1);
			try {
				value = t.getClass().getMethod(getterName).invoke(t).toString();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		sql=sql+set+" where "+idColumn+"='"+value+"'";
		template.update(sql, new SqlPreparedStatementSetter(t,defaultColList));
	}
	
	public void updateObject(String updateSql,String whereClause){
		String sql="update "+datasource.getTable()+" set ";
		sql=sql+updateSql+" where "+whereClause;
		//System.out.println(sql);
		template.update(sql);
	}
	
	public Long getCnt(){
		String sql="select count(*) from "+datasource.getTable()+"";
		Long cnt=template.queryForObject(sql, Long.class);
		return cnt;
	}
	
	public Long getCnt(String whereClause) {
		String sql="select count(*) from "+datasource.getTable()+" where "+whereClause;
		Long cnt=template.queryForObject(sql, Long.class);
		return cnt;
	}
	
	public Object getOne(String whereClause){
		DefaultSqlRowCallBackHandler handler=new DefaultSqlRowCallBackHandler();
		String sql="select "+getDefaultColumn()+" from "+datasource.getTable()+" where "+whereClause;
		IRowRetrieveCallback callback=new IRowRetrieveCallback() {
			List<Object> sinkList=new ArrayList<Object>();
			
			@Override
			public List<Object> sinkList() {
				// TODO Auto-generated method stub
				return sinkList;
			}
			
			@Override
			public void onEachRow(Object row, int rowNum) {
				// TODO Auto-generated method stub
				sinkList.add(row);
			}
			
			@Override
			public Object getObject(int index) {
				// TODO Auto-generated method stub
				return sinkList.get(index);
			}
		};
		handler.setCallback(callback);
		handler.setConfig(config);
		try{
			template.query(sql,handler);
			if(callback.sinkList().size()>=1){
				return callback.getObject(0);
			}else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public void deleteSql(String whereClause) {
		String sql="delete from "+datasource.getTable()+" where "+whereClause;
		//System.out.println(sql);
		template.execute(sql);
	}
	
	public void executeSql(String sql) {
		template.execute(sql);
	}
	




	

	
	public void retrieveRows(IRowRetrieveCallback callback,int start,int limit){
		DefaultSqlRowCallBackHandler handler=new DefaultSqlRowCallBackHandler();
		handler.setCallback(callback);
		handler.setConfig(config);
		template.query("select "+getDefaultColumn()+" from "+datasource.getTable()+" limit "+start+","+limit,handler );
	}
	
	
	public void retrieveRows(IRowRetrieveCallback callback){
		DefaultSqlRowCallBackHandler handler=new DefaultSqlRowCallBackHandler();
		handler.setCallback(callback);
		handler.setConfig(config);
		template.query("select "+getDefaultColumn()+" from "+datasource.getTable(),handler );
	}
	
	
	public void retrieveRowsWithQueryClause(IRowRetrieveCallback callback,String whereClause){
		DefaultSqlRowCallBackHandler handler=new DefaultSqlRowCallBackHandler();
		handler.setCallback(callback);
		handler.setConfig(config);
		String sql="select "+getDefaultColumn()+" from "+datasource.getTable()+" where "+whereClause;
		try{
			template.query(sql,handler );
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	class SqlPreparedStatementSetter implements PreparedStatementSetter{
		Object local;
		List<String> colList;
		SqlPreparedStatementSetter(Object local,List<String> colList){
			this.local=local;
			this.colList=colList;
		}
		
		@Override
		public void setValues(PreparedStatement ps) throws SQLException {
			// TODO Auto-generated method stub
            for(int i=0;i<colList.size();i++){
				String key=colList.get(i);
				String propertyName=config.getMapping(key);
				
				if(local instanceof Map) {
					Map<String,Object> map=(Map<String,Object>)local;
					Object value=map.get(propertyName);
					ps.setObject(i+1, value);
				}else if(local instanceof JSONObject) {
					JSONObject obj=(JSONObject)local;
					Object value=obj.get(propertyName);
					ps.setObject(i+1, value);
				}else {
					String getterName="get"+propertyName.substring(0,1).toUpperCase()+propertyName.substring(1);
					try {
						Object value=local.getClass().getMethod(getterName).invoke(local);
						ps.setObject(i+1, value);
					} catch (Exception e) {
						
						e.printStackTrace();
					} 
					
				}			
			}
		}
		
	}

	public boolean close() {
		// spring jdbc template 会自动释放资源�??
		return true;
	}

}
