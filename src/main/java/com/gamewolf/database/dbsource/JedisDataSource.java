package com.gamewolf.database.dbsource;

import com.gamewolf.database.dbconnector.ConnectionMsg;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisDataSource extends AbstractDataSource{
	
	String host;
	int maxIdle;
	int maxWait;
	boolean testOnBorrow;
	int db=0;
	String schemaName;

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public int getDb() {
		return db;
	}

	public void setDb(int db) {
		this.db = db;
	}

	@Override
	public ConnectionMsg testConnection() {
		// TODO Auto-generated method stub
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(maxIdle);
		config.setMaxWaitMillis(maxWait);
		config.setTestOnBorrow(testOnBorrow);
		JedisPool pool=new JedisPool(config,host);
		ConnectionMsg msg=new ConnectionMsg();
		try{
			String res=pool.getResource().info();
			System.out.println(res);	
			msg.setErrorMsg(res);
			msg.setErrorNum(ConnectionMsg.SUCCESS_NUM);	
		}catch(Exception e){
			msg.setErrorNum(""+99);
			msg.setErrorMsg(e.getMessage()+"");
		}finally{
			pool.close();
		}
		return msg;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	@Override
	public String getConnectionStr() {
		// TODO Auto-generated method stub
		return "redis://"+this.host+":"+6379;
	}
	
	



}
