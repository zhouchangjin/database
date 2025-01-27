package com.gamewolf.database.dbsource;

import com.gamewolf.database.dbconnector.ConnectionMsg;
import com.gamewolf.database.dbmeta.DataSourceType;

public interface ITableDatasource {
	
	String getTable();
	
	void setTable(String table);
	 
	public ConnectionMsg testConnection();
	
	public String getConnectionStr();
	
	DataSourceType getType();

}
