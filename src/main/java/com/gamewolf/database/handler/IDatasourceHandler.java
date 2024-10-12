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
	public void retrieveRows(IRowRetrieveCallback callback,int start,int limit);
	public void retrieveRowsWithQueryClause(IRowRetrieveCallback callback,String whereClause);
	
	void insertIncomplete(Object t);
	void insertObject(Object t);
	void updateObject(String idColumn,Object t);
	void updateObject(String updateSql,String whereClause);
	Long getCnt();
	Long getCnt(String whereClause);
	Object getOne(String whereClause);

}
