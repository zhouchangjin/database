package com.gamewolf.database.handler;

import org.springframework.jdbc.core.JdbcTemplate;
import com.gamewolf.database.dbconnector.ClientManager;
import com.gamewolf.database.dbconnector.ClientProxy;
import com.gamewolf.database.dbconnector.SqliteClientProxy;
import com.gamewolf.database.dbsource.SqliteDataSource;

public class SqliteHandler extends DefaultJDBCDatasourceHandler implements IDatasourceHandler<SqliteDataSource>{
	
	SqliteDataSource datasource;


	@Override
	public void setDatasource(SqliteDataSource datasource) {
		super.datasource=datasource;
		this.datasource=datasource;
	}

	@Override
	public SqliteDataSource getDatasource() {
		// TODO Auto-generated method stub
		return datasource;
	}


	@Override
	public boolean initialize() {
		boolean initializeFlag=false;
		ClientProxy proxy=ClientManager.getClient(datasource);
		if(proxy instanceof SqliteClientProxy){
			SqliteClientProxy sqliteClientProxy=(SqliteClientProxy)proxy;
			this.template=new JdbcTemplate(sqliteClientProxy.getDatasource());
			buildConfig();
			initializeFlag=true;
		}
		return initializeFlag;
	}
	

}
