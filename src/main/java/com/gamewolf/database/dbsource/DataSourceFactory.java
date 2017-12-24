package com.gamewolf.database.dbsource;

import com.gamewolf.database.dbconnector.ConnectionProperties;
import com.gamewolf.database.dbmeta.DataSourceType;
import com.gamewolf.database.dbsource.AbstractDataSource;
import com.gamewolf.database.dbsource.IDataSourceFactory;

public class DataSourceFactory {
	
	public AbstractDataSource getDataSourceByType(DataSourceType type,ConnectionProperties properties) {
		IDataSourceFactory factory=null;
		try {
			
			String typeName=type.getType().toString();
			String typeNamefinal=typeName.substring(0,1).toUpperCase()+typeName.substring(1);
			String packageName="com.gamewolf.database.dbsource.";
			factory = (IDataSourceFactory) Class.forName(packageName+typeNamefinal+"DataSourceFactory").newInstance();
			return factory.createDataSource(type,properties);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	

}
