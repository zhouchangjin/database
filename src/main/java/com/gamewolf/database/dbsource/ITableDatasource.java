package com.gamewolf.database.dbsource;

import com.gamewolf.database.dbconnector.ConnectionMsg;

public interface ITableDatasource {
	
	String getTable();
	
	void setTable(String table);
	 
	public ConnectionMsg testConnection();
	
	public String getConnectionStr();

}
