package com.gamewolf.database.handler;

import java.util.ArrayList;
import java.util.List;

public class DefaultRowRetrieveCallBackHandler implements IRowRetrieveCallback {
	
	public List<Object> sinkList=new ArrayList<Object>();

	@Override
	public void onEachRow(Object row, int rowNum) {
		// TODO Auto-generated method stub
		sinkList.add(row);
	}

	@Override
	public List<Object> sinkList() {
		// TODO Auto-generated method stub
		return sinkList;
	}

	@Override
	public Object getObject(int index) {
		// TODO Auto-generated method stub
		return sinkList.get(index);
	}

}
