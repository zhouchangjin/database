package com.gamewolf.database.dbsource;

import com.gamewolf.database.dbconnector.ConnectionProperties;
import com.gamewolf.database.dbmeta.DataSourceType;
import com.gamewolf.database.dbsource.AbstractDataSource;
import com.gamewolf.database.dbsource.IDataSourceFactory;
import com.gamewolf.database.dbsource.JedisDataSource;

public class JedisDataSourceFactory implements IDataSourceFactory{

	public AbstractDataSource createDataSource(DataSourceType type,
			ConnectionProperties prop) {
		// TODO Auto-generated method stub
		String ipStr=prop.getProperty("jedis.host");
		String maxIdle=prop.getProperty("jedis.max_idle");
		String maxWait=prop.getProperty("jedis.max_wait");
		String testOnBorrow=prop.getProperty("jedis.test_on_borrow");
		String dbStr=prop.getProperty("jedis.db");
		String schema=prop.getProperty("jedis.schema");
		JedisDataSource datasource=new JedisDataSource();
		if(dbStr!=null && !dbStr.equals("")){
			int dbNum=Integer.parseInt(dbStr);
			datasource.setDb(dbNum);
			datasource.setSchemaName(schema);
		}
		datasource.setHost(ipStr);
		datasource.setMaxIdle(Integer.parseInt(maxIdle));
		datasource.setMaxWait(Integer.parseInt(maxWait));
		datasource.setTestOnBorrow(testOnBorrow.equals("true"));
		datasource.setType(type);
		return datasource;
	}

}
