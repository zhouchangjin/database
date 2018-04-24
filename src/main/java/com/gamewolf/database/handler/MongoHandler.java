package com.gamewolf.database.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gamewolf.database.dbconnector.ClientManager;
import com.gamewolf.database.dbconnector.ClientProxy;
import com.gamewolf.database.dbconnector.MongoClientProxy;
import com.gamewolf.database.dbsource.MongoDBDataSource;
import com.gamewolf.database.entity.BasicObject;
import com.gamewolf.database.util.JSONUtil;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

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
		DBCursor cursor=this.op.getCollection(this.getDatasource().getCollection()).find();
		while(cursor.hasNext()) {
			DBObject obj=cursor.next();
			BasicObject bo=buildBasicObject(obj);
			list.add(bo);
		}
		return list;
	}
	
	private void processAlibabaJSONArray(JSONArray array,BasicDBList list) {
		for(Object obj:array) {
			if(obj instanceof JSONObject) {
				BasicDBObject mongoObj=new BasicDBObject();
				processAlibabaJSONObject((JSONObject) obj,mongoObj);
				list.add(mongoObj);
			}else if(obj instanceof JSONArray) {
				BasicDBList mongoList=new BasicDBList();
				processAlibabaJSONArray((JSONArray)obj, mongoList);
				list.add(mongoList);
			}else{
				list.add(obj);
			}
		}
	}
	private void processAlibabaJSONObject(JSONObject jsonObj,BasicDBObject mongoObj) {
		Iterator<String>propIt=jsonObj.keySet().iterator();
		while(propIt.hasNext()) {
			String prop=propIt.next();
			Object value=jsonObj.get(prop);
			if(value instanceof JSONObject) {
				BasicDBObject basicDBObject=new BasicDBObject();
				mongoObj.put(prop, basicDBObject);
				processAlibabaJSONObject((JSONObject)value, basicDBObject);
			}else if(value instanceof JSONArray) {
				JSONArray array=(JSONArray)value;
				BasicDBList dbList=new BasicDBList();
				mongoObj.put(prop, dbList);
				processAlibabaJSONArray(array, dbList);
			}else {
				mongoObj.put(prop, value);
			}
		}
	}
	public void insert(Object obj) {
		BasicDBObject db=new BasicDBObject();
		ObjectId id=new ObjectId();
		db.put("_id", id);
		if(obj instanceof String) {
			String str=(String)obj;
			JSONObject json=JSON.parseObject(str);
			processAlibabaJSONObject(json, db);
		}else if(obj instanceof JSONObject){
			JSONObject json=(JSONObject)obj;
			processAlibabaJSONObject(json, db);
		}else if(obj instanceof HashMap) {
			
		}else {
			
		}
		this.op.getCollection(this.getDatasource().getCollection()).insert(db);
	}

	private BasicObject buildBasicObject(DBObject object) {
		BasicObject bo=BasicObject.buildBasicObject();
		Iterator<String> it=object.keySet().iterator();
		while(it.hasNext()) {
			String key=it.next();
			Object obj=object.get(key);
			if(obj instanceof DBObject) {
				DBObject bdb=(DBObject)obj;
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
