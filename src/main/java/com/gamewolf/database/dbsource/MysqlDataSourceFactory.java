/**
 * 
 */
package com.gamewolf.database.dbsource;

import com.gamewolf.database.dbconnector.ConnectionProperties;
import com.gamewolf.database.dbmeta.DataSourceType;
import com.gamewolf.database.dbsource.AbstractDataSource;
import com.gamewolf.database.dbsource.IDataSourceFactory;
import com.gamewolf.database.dbsource.MysqlDataSource;

/**
 * @author 
 *
 */

public class MysqlDataSourceFactory implements IDataSourceFactory {


	@Override
	public AbstractDataSource createDataSource(DataSourceType type,
			ConnectionProperties prop) {
		
		String ipStr=prop.getProperty("mysql.host");
		String portStr=prop.getProperty("mysql.port");
		String userName=prop.getProperty("mysql.username");
		String password=prop.getProperty("mysql.password");
		String database=prop.getProperty("mysql.database");
		String table=prop.getProperty("mysql.table");
		MysqlDataSource datasource=new MysqlDataSource();
		datasource.setDatabase(database);
		datasource.setHost(ipStr);
		datasource.setPort(Integer.parseInt(portStr));
		datasource.setTable(table);
		datasource.setPassword(password);
		datasource.setUsername(userName);
		DataSourceType datasourceType=new DataSourceType();
		datasourceType.setType(DataSourceType.MYSQL);
		datasource.setType(datasourceType);
		return datasource;
	}

}
