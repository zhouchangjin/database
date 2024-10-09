package com.gamewolf.database.dbsource;


import com.gamewolf.database.dbconnector.ConnectionProperties;
import com.gamewolf.database.dbmeta.DataSourceType;

public class SqliteDataSourceFactory implements IDataSourceFactory{

	@Override
	public AbstractDataSource createDataSource(DataSourceType type, ConnectionProperties prop) {
		// TODO Auto-generated method stub
		String path=prop.getProperty("sqlite.filePath");

		SqliteDataSource sqliteDataSource=new SqliteDataSource();
		sqliteDataSource.setDatabaseFile(path);
		DataSourceType datasourceType=new DataSourceType();
		datasourceType.setType(DataSourceType.SQLITE);
		sqliteDataSource.setType(datasourceType);
		return sqliteDataSource;
	}

}
