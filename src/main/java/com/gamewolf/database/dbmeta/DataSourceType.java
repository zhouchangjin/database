package com.gamewolf.database.dbmeta;

public class DataSourceType {
	public final static String MONGO="mongoDB";
	public final static String JDBC="jdbc";
	public final static String ELASTIC_SEARCH="elasticSearch";
	public final static String JEDIS="jedis";
	public final static String MYSQL="mysql";
	String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
