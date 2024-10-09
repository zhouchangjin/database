package com.gamewolf.database.test;


import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.gamewolf.database.dbconnector.ConnectionProperties;
import com.gamewolf.database.dbmeta.DataSourceType;
import com.gamewolf.database.dbsource.DataSourceFactory;
import com.gamewolf.database.dbsource.SqliteDataSource;
import com.gamewolf.database.handler.MappingConfig;
import com.gamewolf.database.handler.SqliteHandler;

public class SqliteHandlerTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SqliteHandler handler=new SqliteHandler();
		MappingConfig mappingConfig=new MappingConfig();
		mappingConfig.setMappingClazz(Book.class);
		ConnectionProperties connectionProperties=new ConnectionProperties();
		connectionProperties.putProperty("sqlite.filePath", "D:/Dev/Database/sqlite-gui-1.9.2-x64/examples/bookstore.sqlite");
		DataSourceFactory dataSourceFactory=new DataSourceFactory();
		SqliteDataSource sqliteDataSource=(SqliteDataSource) dataSourceFactory.
				getDataSourceByType(DataSourceType.sqlite(), connectionProperties);
		sqliteDataSource.setTable("books");
		handler.setDatasource(sqliteDataSource);
		handler.SetConfig(mappingConfig);
		handler.initialize();
		List<Object> books=handler.preview();
		for(Object o:books) {
			Book book=(Book)o;
			System.out.println(JSONObject.toJSONString(book));
		}
	}

}
