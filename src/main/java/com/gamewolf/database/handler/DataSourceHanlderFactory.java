package com.gamewolf.database.handler;

import com.gamewolf.database.dbmeta.DataSourceType;
import com.gamewolf.database.dbsource.ITableDatasource;

public class DataSourceHanlderFactory {
	
	public IDatasourceHandler<?> createDatasourceHandler(ITableDatasource datasource){
		DataSourceType type=	datasource.getType();
		if(type.getType().equals(DataSourceType.MYSQL)) {
			return new MySqlHandler();
		}else if(type.getType().equals(DataSourceType.SQLITE)){
			return new SqliteHandler();
		}else {
			return null;
		}
	}

}
