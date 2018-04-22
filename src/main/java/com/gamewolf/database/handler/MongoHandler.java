package com.gamewolf.database.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;

import com.gamewolf.database.dbconnector.ClientManager;
import com.gamewolf.database.dbconnector.ClientProxy;
import com.gamewolf.database.dbconnector.MongoClientProxy;
import com.gamewolf.database.dbsource.MongoDBDataSource;
import com.gamewolf.database.entity.BasicObject;
import com.mongodb.BasicDBObject;

public class MongoHandler implements IDatasourceHandler<MongoDBDataSource> {
	
	MongoDBDataSource datasource;
	MongoClientProxy client;
	MongoOperations op;
	Class mappingClass=null;
	
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
		List<BasicDBObject> mylist=this.op.find(bq,BasicDBObject.class );
		for(BasicDBObject object:mylist) {
			BasicObject bo=buildBasicObject(object);
			list.add(bo);
		}
		return list;
	}
	
	public void insert(Object obj) {
		this.op.insert(obj);
	}

	private BasicObject buildBasicObject(BasicDBObject object) {
		BasicObject bo=BasicObject.buildBasicObject();
		Iterator<String> it=object.keySet().iterator();
		while(it.hasNext()) {
			String key=it.next();
			Object obj=object.get(key);
			if(obj instanceof BasicDBObject) {
				BasicDBObject bdb=(BasicDBObject)obj;
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

}
