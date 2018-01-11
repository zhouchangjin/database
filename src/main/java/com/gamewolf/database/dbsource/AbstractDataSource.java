package com.gamewolf.database.dbsource;

import com.gamewolf.database.dbconnector.ConnectionMsg;
import com.gamewolf.database.dbmeta.DataSourceType;

public abstract class AbstractDataSource implements Cloneable{
	DataSourceType type;

	public DataSourceType getType() {
		return type;
	}

	public void setType(DataSourceType type) {
		this.type = type;
	}
	
	public abstract ConnectionMsg testConnection();
	
	public abstract String getConnectionStr();
	
	public AbstractDataSource clone() {
		try {
			return (AbstractDataSource) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			return null;
		}
		
	}
	

}
