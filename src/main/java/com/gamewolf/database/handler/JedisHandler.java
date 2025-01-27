package com.gamewolf.database.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.gamewolf.database.dbconnector.ClientManager;
import com.gamewolf.database.dbconnector.ClientProxy;
import com.gamewolf.database.dbconnector.ConnectionMsg;
import com.gamewolf.database.dbconnector.JedisClientProxy;
import com.gamewolf.database.dbsource.ITableDatasource;
import com.gamewolf.database.dbsource.JedisDataSource;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisHandler implements IDatasourceHandler<JedisDataSource>{
	
	public static int PREVIEWSIZE=10;
	
	JedisDataSource datasource;
	JedisPool jedisPool;
	Jedis jedis;

	@Override
	public void setDatasource(JedisDataSource datasource) {
		// TODO Auto-generated method stub
		this.datasource=datasource;
	}

	@Override
	public JedisDataSource getDatasource() {
		// TODO Auto-generated method stub
		return datasource;
	}

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		if(datasource.testConnection().getErrorNum().equals(ConnectionMsg.SUCCESS_NUM)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean initialize() {
		// TODO Auto-generated method stub
		boolean initializeFlag=false;
		ClientProxy proxy=ClientManager.getClient(datasource);
		if(proxy instanceof JedisClientProxy){
			JedisClientProxy jedisClient=(JedisClientProxy)proxy;
			this.jedisPool=jedisClient.getJedisPool();
			this.jedis=jedisPool.getResource();
			int db=datasource.getDb();
			jedis.select(db);
		}
		return initializeFlag;
	}
	
	public <T> boolean push(T t){
		 try {  
	            jedis.lpush(datasource.getSchemaName(), JSON.toJSONString(t));  
	        } catch(Exception e){
	        	jedis.close();
	        	jedis=jedisPool.getResource();
	        	return false;
	        }
		 return true;
	
	}
	
	public <T>T  pop() {
		Class clazz=null;
		try {
			clazz = getClass().getMethod("pop").getReturnType();
			return (T)JSON.parseObject(jedis.rpop(datasource.getSchemaName()),clazz);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return null;
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	public <T>T popQueue(){
		return pop();
	}
	
	public <T>T popStack(){
		Class clazz=null;
		try {
			clazz = getClass().getMethod("pop").getReturnType();
			return (T)JSON.parseObject(jedis.lpop(datasource.getSchemaName()),clazz);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return null;
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	 public void addSetElement(String... setvalue){
	    	try{
	    		jedis.sadd(datasource.getSchemaName(), setvalue);
	    		
	    	}catch(Exception e){
	    		e.printStackTrace();
	    		jedis.close();
	    		jedis=jedisPool.getResource();
	    	}
	 }
	 
	 public void addMapElement(String key,String value) {
		jedis.hset(datasource.getSchemaName(), key, value);
	 }
	 
	 public Map<String,String> getMap() {
		 return jedis.hgetAll(datasource.getSchemaName());
	 }
	

	
	

	@Override
	public List<Object> preview() {
		Set<String> list=jedis.keys(datasource.getSchemaName()+"*");
		List<Object> re=new ArrayList<Object>();
		re.addAll(list);
		return re;
	}

	@Override
	public void retrieveRows(IRowRetrieveCallback callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean close() {
		// TODO Auto-generated method stub
		this.jedis.close();
		return true;
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

	@Override
	public void setConfig(MappingConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTableDatasource(ITableDatasource tableDatasource) {
		// TODO Auto-generated method stub
		
	}

}
