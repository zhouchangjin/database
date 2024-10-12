package com.gamewolf.database.handler;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.gamewolf.database.dbconnector.ClientManager;
import com.gamewolf.database.dbconnector.ClientProxy;
import com.gamewolf.database.dbconnector.ConnectionMsg;
import com.gamewolf.database.dbconnector.SqliteClientProxy;
import com.gamewolf.database.dbsource.SqliteDataSource;

public class SqliteHandler implements IDatasourceHandler<SqliteDataSource>{
	public static int PREVIEWSIZE=10;
	JdbcTemplate template;
	SqliteDataSource datasource;
	MappingConfig config;
	String defaultColStr="";
	List<String> defaultColList=new ArrayList<String>();
	
	public void SetConfig(MappingConfig config) {
		this.config=config;
	}

	@Override
	public void setDatasource(SqliteDataSource datasource) {
		// TODO Auto-generated method stub
		this.datasource=datasource;
	}

	@Override
	public SqliteDataSource getDatasource() {
		// TODO Auto-generated method stub
		return datasource;
	}

	@Override
	public boolean isConnected() {
		if(datasource.testConnection().getErrorNum().equals(ConnectionMsg.SUCCESS_NUM)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean initialize() {
		boolean initializeFlag=false;
		ClientProxy proxy=ClientManager.getClient(datasource);
		if(proxy instanceof SqliteClientProxy){
			SqliteClientProxy sqliteClientProxy=(SqliteClientProxy)proxy;
			this.template=new JdbcTemplate(sqliteClientProxy.getDatasource());
			buildConfig();
			initializeFlag=true;
		}
		return initializeFlag;
	}
	
	void buildConfig(){
		template.query("select * from "+datasource.getTable()+" limit 0,1", new RowMapper<Object>(){
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

	@Override
	public boolean close() {
		return true;
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
	
	public String getDefaultColumn(){
		if(defaultColStr.equals("")){
			buildColumns();
		}
		return defaultColStr;
	}

	@Override
	public List<Object> preview() {
		// TODO Auto-generated method stub
		List<Object> list=template.query("select "+getDefaultColumn()+"  from "+datasource.getTable()+" limit 0,"+PREVIEWSIZE, new RowMapper<Object>(){
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				
					try {
						Object obj=config.getMappingClazz().newInstance();
						ResultSetMetaData metaData=rs.getMetaData();
						for(int i=1;i<=metaData.getColumnCount();i++){
							String colSql=metaData.getColumnName(i);
							String mappedAttr=config.getMapping(colSql);
							if(rs.getObject(i)!=null)
							BeanUtils.setProperty(obj, mappedAttr, rs.getObject(i));
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

	@Override
	public void retrieveRows(IRowRetrieveCallback callback) {
		MysqlRowCallBackHandler handler=new MysqlRowCallBackHandler();
		handler.setCallback(callback);
		handler.setConfig(config);
		template.query("select "+getDefaultColumn()+" from "+datasource.getTable(),handler );
		
	}

	@Override
	public void retrieveRows(IRowRetrieveCallback callback, int start, int limit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void retrieveRowsWithQueryClause(IRowRetrieveCallback callback, String whereClause) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertIncomplete(Object t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertObject(Object t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateObject(String idColumn, Object t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateObject(String updateSql, String whereClause) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getCnt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getCnt(String whereClause) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getOne(String whereClause) {
		// TODO Auto-generated method stub
		return null;
	}

}
