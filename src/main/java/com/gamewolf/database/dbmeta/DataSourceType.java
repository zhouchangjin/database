package com.gamewolf.database.dbmeta;

public class DataSourceType {
	public final static String MONGO="mongoDB";
	public final static String JDBC="jdbc";
	public final static String ELASTIC_SEARCH="elasticSearch";
	public final static String JEDIS="jedis";
	public final static String MYSQL="mysql";
	public final static String SQLITE="sqlite";
	String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public static DataSourceType buildDataSourceType(String type) {
		DataSourceType dstype=new DataSourceType();
		dstype.setType(type);
		return dstype;
	}
	
	public static DataSourceType mongo() {
		DataSourceType dstype=new DataSourceType();
		dstype.setType(DataSourceType.MONGO	);
		return dstype;
	}
	
	public static DataSourceType jedis() {
		DataSourceType dstype=new DataSourceType();
		dstype.setType(DataSourceType.JEDIS);
		return dstype;
	}
	
	public static DataSourceType sqlite() {
		DataSourceType dstype=new DataSourceType();
		dstype.setType(DataSourceType.SQLITE);
		return dstype;
	}
	
	public static DataSourceType mysql() {
		DataSourceType dstype=new DataSourceType();
		dstype.setType(DataSourceType.MYSQL);
		return dstype;
	}
	
	public static DataSourceType elasticSearch() {
		DataSourceType dstype=new DataSourceType();
		dstype.setType(DataSourceType.ELASTIC_SEARCH);
		return dstype;
	}

}
