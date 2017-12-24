package com.gamewolf.database.handler;

import java.util.List;

public interface IRowRetrieveCallback {
	
	
	public void onEachRow(Object row,int rowNum);
	
	public List<Object> sinkList();
	
	public Object getObject(int index);

}
