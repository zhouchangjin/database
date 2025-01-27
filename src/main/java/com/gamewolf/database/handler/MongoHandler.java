package com.gamewolf.database.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;

import com.gamewolf.database.dbconnector.ClientManager;
import com.gamewolf.database.dbconnector.ClientProxy;
import com.gamewolf.database.dbconnector.MongoClientProxy;
import com.gamewolf.database.dbsource.ITableDatasource;
import com.gamewolf.database.dbsource.MongoDBDataSource;
import com.gamewolf.database.entity.BasicObject;
import com.mongodb.client.FindIterable;

public class MongoHandler implements IDatasourceHandler<MongoDBDataSource> {
	
	MongoDBDataSource datasource;
	MongoClientProxy client;
	MongoOperations op;
	Class mappingClass=null;
	
	/**
	 * 暂时还未使用
	 * @param mappingClass
	 */
	public void setMappingClass(Class mappingClass) {
		this.mappingClass=mappingClass;
	}
	

	@Override
	public void setDatasource(MongoDBDataSource datasource) {
		this.datasource=datasource;
	}

	@Override
	public MongoDBDataSource getDatasource() {
		// TODO Auto-generated method stub
		return this.datasource;
	}

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean initialize() {
		// TODO Auto-generated method stub
		boolean initializeFlag=false;
		ClientProxy proxy=ClientManager.getClient(datasource);
		if(proxy instanceof MongoClientProxy){
			client=(MongoClientProxy)proxy;
			op=client.getMongoOperation();
			initializeFlag=true;
		}
		return initializeFlag;
	}

	@Override
	public boolean close() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Object> preview() {
		List<Object> list=new ArrayList<Object>();
		BasicQuery bq=new BasicQuery("{}") ;
		bq.skip(0).limit(10);
		FindIterable<Document> cursor=this.op.getCollection(this.getDatasource().getCollection()).find();
		Iterator<Document> it=cursor.iterator();
		while(it.hasNext()) {
			Document obj=it.next();
			BasicObject bo=buildBasicObject(obj);
			list.add(bo);
		}
		return list;
	}
	
	
	
	public <T> T getOne(String id,Class<T> type) {
		
		return this.op.findById(id, type, this.getDatasource().getCollection());
	}
	

	
	
	public void save(Object obj) {
		this.op.save(obj,this.getDatasource().getCollection());
	}
	
	public void insert(Object obj) {
		try {
			this.op.insert(obj,this.getDatasource().getCollection());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	private BasicObject buildBasicObject(Document object) {
		BasicObject bo=BasicObject.buildBasicObject();
		Iterator<String> it=object.keySet().iterator();
		while(it.hasNext()) {
			String key=it.next();
			Object obj=object.get(key);
			if(obj instanceof Document) {
				Document bdb=(Document)obj;
				bo.set(key, buildBasicObject(bdb));
			}else {
				bo.set(key, obj);
			}
		}
		return bo;
	}


	@Override
	public void retrieveRows(IRowRetrieveCallback callback) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void retrieveRows(IRowRetrieveCallback callback, int start, int limit) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void retrieveRowsWithQueryClause(IRowRetrieveCallback callback, String whereClause) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void insertIncomplete(Object t) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void insertObject(Object t) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateObject(String idColumn, Object t) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateObject(String updateSql, String whereClause) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Long getCnt() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Long getCnt(String whereClause) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Object getOne(String whereClause) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setConfig(MappingConfig config) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setTableDatasource(ITableDatasource tableDatasource) {
		// TODO Auto-generated method stub
		
	}

}
