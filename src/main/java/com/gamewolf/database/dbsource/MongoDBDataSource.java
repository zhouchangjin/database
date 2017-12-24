package com.gamewolf.database.dbsource;

import java.util.Date;

import com.gamewolf.database.dbconnector.ConnectionMsg;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class MongoDBDataSource extends AbstractDataSource {
	
	String host;
	Integer port;
	String username;
	String password;
	String database;
	String collection;
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public MongoDBDataSource() {
		super();
	}

	@Override
	public ConnectionMsg testConnection(){
		// TODO Auto-generated method stub
		//MongoClient client=new MongoClient(new ServerAddress(this.host,port));
	
		try{
			MongoClient client=new MongoClient(this.host, port);
			ServerAddress master=client.getAddress();
			ConnectionMsg msg=new ConnectionMsg();
			msg.setErrorNum(ConnectionMsg.SUCCESS_NUM);
			msg.setErrorMsg(new Date().toLocaleString()+"连接"+master.getHost()+":"+master.getPort());
			return msg;
		}catch(Exception e){
			ConnectionMsg msg=new ConnectionMsg();
			msg.setErrorMsg("数据库连接失败");
			msg.setErrorNum("101");
			return msg;
		}

	}

	@Override
	public String getConnectionStr() {
		// TODO Auto-generated method stub
		return "mongo://"+this.host+":"+this.port+"/?username="+this.username+"&password="+this.password;
	}

}
