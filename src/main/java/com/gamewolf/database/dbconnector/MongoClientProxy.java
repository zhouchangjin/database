package com.gamewolf.database.dbconnector;



import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.gamewolf.database.dbsource.AbstractDataSource;
import com.gamewolf.database.dbsource.MongoDBDataSource;
import com.mongodb.MongoClient;

public class MongoClientProxy extends ClientProxy{
	
	MongoOperations operation=null;
	
	public MongoOperations getMongoOperation() {
		return operation;
	}

	public MongoClientProxy(AbstractDataSource abstractDatasource) {
		super(abstractDatasource);
		if(abstractDatasource instanceof MongoDBDataSource) {
			MongoDBDataSource mdb=(MongoDBDataSource)abstractDatasource;
			String ip=mdb.getHost();
			int port=mdb.getPort();
			MongoClient client=new MongoClient(ip,port);
			MongoDbFactory factory=new SimpleMongoDbFactory(client, mdb.getDatabase());
			operation=new MongoTemplate(factory);
		}
	}

}
