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
		//mappingConfig.setMappingClazz(Book.class);
		mappingConfig.setMappingClazz(JSONObject.class);
		ConnectionProperties connectionProperties=new ConnectionProperties();
		connectionProperties.putProperty("sqlite.filePath", "D:/Dev/Database/sqlite-gui-1.9.2-x64/examples/bookstore.sqlite");
		DataSourceFactory dataSourceFactory=new DataSourceFactory();
		SqliteDataSource sqliteDataSource=(SqliteDataSource) dataSourceFactory.
				getDataSourceByType(DataSourceType.sqlite(), connectionProperties);
		sqliteDataSource.setTable("books");
		handler.setDatasource(sqliteDataSource);
		handler.setConfig(mappingConfig);
		handler.initialize();
		List<Object> books=handler.preview();
		for(Object o:books) {
			//Book book=(Book)o;
			//System.out.println(JSONObject.toJSONString(book));
			System.out.println(o.toString());
		}
		System.out.println("===================");
		System.out.println(handler.getOne("id=2"));

		System.out.println("===================");
		JSONObject object=new JSONObject();
		object.put("author", "zcj2");
		object.put("price", 2.4);
		object.put("isbn", "97801222");
		object.put("id", 220009);
		object.put("available", 11);
		object.put("title", "aa");
		handler.insertIncomplete(object);
		System.out.println(handler.getOne("author='zcj'"));
		
		//System.out.print(((Book)handler.getOne("id=2")).getAuthor());
	}

}
