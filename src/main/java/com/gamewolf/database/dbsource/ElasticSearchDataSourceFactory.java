package com.gamewolf.database.dbsource;

import com.gamewolf.database.dbconnector.ConnectionProperties;
import com.gamewolf.database.dbmeta.DataSourceType;
import com.gamewolf.database.dbsource.AbstractDataSource;
import com.gamewolf.database.dbsource.ElasticSearchDataSource;
import com.gamewolf.database.dbsource.IDataSourceFactory;

public class ElasticSearchDataSourceFactory implements IDataSourceFactory {

	public AbstractDataSource createDataSource(DataSourceType type,
			ConnectionProperties prop) {
		String ipStr=prop.getProperty("es.host");
		String portStr=prop.getProperty("es.port");
		String indexStr=prop.getProperty("es.index");
		String typeStr=prop.getProperty("es.type");
		
		ElasticSearchDataSource datasource=new ElasticSearchDataSource();
		datasource.setHost(ipStr);
		datasource.setPort(Integer.parseInt(portStr));
		datasource.setIndexName(indexStr);
		datasource.setTypeName(typeStr);
		datasource.setType(type);
		if(prop.getProperty("es.cluster")!=null && !prop.getProperty("es.cluster").equals("")) {
			String clusterName=prop.getProperty("es.cluster");
			datasource.setClusterName(clusterName);
		}
		return (AbstractDataSource)datasource;
	}

}
