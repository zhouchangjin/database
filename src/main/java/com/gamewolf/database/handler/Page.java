package com.gamewolf.database.handler;

import java.util.List;

public class Page<T> {
	
	public long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}

	public Page(long totalRecord,long totalPage,long page,int pageSize){
		this.total=totalPage;
		this.totalRecord=totalRecord;
		this.page=page;
		this.pageDef=new PageMeta(pageSize);

	}
	
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public PageMeta getPageDef() {
		return pageDef;
	}
	public void setPageDef(PageMeta pageDef) {
		this.pageDef = pageDef;
	}

	long total;
	long page;
	long totalRecord;
	
	PageMeta pageDef;
	List<T> list; 
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public long getPage() {
		return page;
	}
	public void setPage(long page) {
		this.page = page;
	}

}
