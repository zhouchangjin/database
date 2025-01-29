package com.gamewolf.database.handler.init;

import java.lang.reflect.Field;

import com.gamewolf.database.dbconnector.ConnectionProperties;
import com.gamewolf.database.dbsource.DataSourceFactory;
import com.gamewolf.database.dbsource.SqliteDataSource;
import com.gamewolf.database.handler.MappingConfig;
import com.gamewolf.database.handler.SqliteHandler;
import com.gamewolf.database.orm.annotation.SqliteTableBinding;

public class SqliteInitializer {
	
	public static DataSourceFactory factory = new DataSourceFactory();
	
	public void initSqliteHandler() {
		
		Field f[]=this.getClass().getFields();
		for(Field field:f) {
			if(field.getType().equals(SqliteHandler.class) && field.isAnnotationPresent(SqliteTableBinding.class)) {
				SqliteTableBinding binding=field.getAnnotation(SqliteTableBinding.class);
				Class t=binding.javaClass();
				MappingConfig config=new MappingConfig();
				config.setMappingClazz(t);
				String table=binding.table();
				String propP=binding.propertiePath();
				String prop=binding.propertieFile();
				boolean isRes=binding.isResource();
				
				ConnectionProperties sqliteprop=ConnectionProperties.loadPropertiesFromPropetiesFile(propP, prop, isRes);
				//MysqlDataSource datasource=(MysqlDataSource)factory.getDataSourceByType(DataSourceType.mysql(), mysqlProp);
				SqliteDataSource datasource=(SqliteDataSource) factory.getDataSourceByConnectionProperties(sqliteprop);
				datasource.setTable(table);
				SqliteHandler handler=new SqliteHandler();
				handler.setConfig(config);
				handler.setDatasource(datasource);
				handler.initialize();
				try {
					field.set(this, handler);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}
			}
		}
	
	}

}
