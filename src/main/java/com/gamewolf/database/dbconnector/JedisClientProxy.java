package com.gamewolf.database.dbconnector;

import com.gamewolf.database.dbsource.AbstractDataSource;
import com.gamewolf.database.dbsource.JedisDataSource;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisClientProxy extends ClientProxy{
	
	JedisPool pool;
	
	public JedisPool getJedisPool(){
		return pool;
	}

	public JedisClientProxy(AbstractDataSource abstractDatasource) {
		super(abstractDatasource);
		// TODO Auto-generated constructor stub
		if(abstractDatasource instanceof JedisDataSource){
			JedisDataSource datasource=(JedisDataSource)abstractDatasource;
			JedisPoolConfig config = new JedisPoolConfig();
			String ip=datasource.getHost();
			int maxIdle=datasource.getMaxIdle();
			int maxWait=datasource.getMaxWait();
			boolean test_on_borrow=datasource.isTestOnBorrow();
			config.setMaxIdle(maxIdle);
			config.setMaxWaitMillis(maxWait);
		    config.setTestOnBorrow(test_on_borrow);
			pool =new JedisPool(config,ip);
			
		}
	}

}
