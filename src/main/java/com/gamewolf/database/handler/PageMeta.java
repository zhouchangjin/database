package com.gamewolf.database.handler;

public class PageMeta {
	
	PageMeta(int pageSize){
		this.pageSize=pageSize;
	}
	
	int pageSize;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
