package com.gamewolf.database.dbsource;

import com.gamewolf.database.dbconnector.ConnectionProperties;
import com.gamewolf.database.dbmeta.DataSourceType;
import com.gamewolf.database.dbsource.AbstractDataSource;
import com.gamewolf.database.dbsource.IDataSourceFactory;
import com.gamewolf.database.dbsource.MongoDBDataSource;

public class MongoDBDataSourceFactory implements IDataSourceFactory {

	public AbstractDataSource createDataSource(DataSourceType type,ConnectionProperties prop) {
		// TODO Auto-generated method stub
		String ipStr=prop.getProperty("mongo.host");
		String portStr=prop.getProperty("mongo.port");
		String userName=prop.getProperty("mongo.username");
		String password=prop.getProperty("mongo.password");
		String database=prop.getProperty("mongo.database");
		String collection=prop.getProperty("mongo.collection");
		MongoDBDataSource datasource=new MongoDBDataSource();
		datasource.setCollection(collection);
		datasource.setDatabase(database);
		datasource.setHost(ipStr);
		try{
			datasource.setPort(Integer.parseInt(portStr));
		}catch (Exception e){
			datasource.setPort(0);
		}
		
		datasource.setType(type);
		datasource.setUsername(userName);
		datasource.setPassword(password);
		return datasource;
	}

}
