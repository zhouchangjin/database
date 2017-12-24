package com.gamewolf.database.handler;

import java.util.List;

import com.gamewolf.database.dbsource.AbstractDataSource;


public interface IDatasourceHandler<T extends AbstractDataSource> {
	
	public void setDatasource(T datasource);
	public T getDatasource();
	public boolean isConnected();
	public boolean initialize();
	public boolean close();
	public List<Object> preview();
	public void retrieveRows(IRowRetrieveCallback callback);
	
	
	

}
