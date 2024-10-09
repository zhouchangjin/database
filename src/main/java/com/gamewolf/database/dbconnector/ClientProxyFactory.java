package com.gamewolf.database.dbconnector;

import com.gamewolf.database.dbmeta.DataSourceType;
import com.gamewolf.database.dbsource.AbstractDataSource;

public class ClientProxyFactory {
	
	public ClientProxy createClientByType(AbstractDataSource datasource){
		if(datasource.getType().getType().equals(DataSourceType.MYSQL)){
			return new MysqlClientProxy(datasource);
		}else if(datasource.getType().getType().equals(DataSourceType.JEDIS)){
			return new JedisClientProxy(datasource);
		}else if(datasource.getType().getType().equals(DataSourceType.ELASTIC_SEARCH)){
			return new ElasticSearchClientProxy(datasource);
		}else if(datasource.getType().getType().equals(DataSourceType.MONGO)) {
			return new MongoClientProxy(datasource);
		}else if(datasource.getType().getType().equals(DataSourceType.SQLITE)) {
			return new SqliteClientProxy(datasource);
		}else{
			return new ClientProxy(datasource);
		}
		
	}

}
