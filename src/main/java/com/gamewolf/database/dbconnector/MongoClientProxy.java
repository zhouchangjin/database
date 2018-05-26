package com.gamewolf.database.dbconnector;



import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.gamewolf.database.dbsource.AbstractDataSource;
import com.gamewolf.database.dbsource.MongoDBDataSource;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

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
			String userName=mdb.getUsername();
			String password=mdb.getPassword();
			//MongoClientOptions options=new MongoClientOptions.Builder().build();
			MongoClient mongoClient=null;
			if(userName!=null && !userName.equals("")) {
				MongoCredential credential = MongoCredential.createMongoCRCredential(userName, mdb.getDatabase(), password.toCharArray());
				List<MongoCredential> list=new ArrayList<MongoCredential>();
				list.add(credential);
				mongoClient = new MongoClient(new ServerAddress(ip, port), list);
			}else {
				mongoClient=new MongoClient(ip,port);
			}
			MongoDbFactory factory=new SimpleMongoDbFactory(mongoClient, mdb.getDatabase());
			operation=new MongoTemplate(factory);
		}
	}

}
