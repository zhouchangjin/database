package com.gamewolf.database.dbsource;

import com.gamewolf.database.dbconnector.ConnectionProperties;
import com.gamewolf.database.dbmeta.DataSourceType;

public interface IDataSourceFactory {
	
	AbstractDataSource createDataSource(DataSourceType type,ConnectionProperties prop);

}
