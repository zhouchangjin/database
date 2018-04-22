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
			Iterator<String> keyset=object.keySet().iterator();
		}
		return list;
	}

	@Override
	public void retrieveRows(IRowRetrieveCallback callback) {
		// TODO Auto-generated method stub
		
	}

}
