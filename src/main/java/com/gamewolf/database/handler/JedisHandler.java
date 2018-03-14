package com.gamewolf.database.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSON;
import com.gamewolf.database.dbconnector.ClientManager;
import com.gamewolf.database.dbconnector.ClientProxy;
import com.gamewolf.database.dbconnector.ConnectionMsg;
import com.gamewolf.database.dbconnector.JedisClientProxy;
import com.gamewolf.database.dbsource.JedisDataSource;

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

}
