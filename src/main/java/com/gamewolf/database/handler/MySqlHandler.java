package com.gamewolf.database.handler;

import org.springframework.jdbc.core.JdbcTemplate;
import com.gamewolf.database.dbconnector.ClientManager;
import com.gamewolf.database.dbconnector.ClientProxy;
import com.gamewolf.database.dbconnector.MysqlClientProxy;
import com.gamewolf.database.dbsource.ITableDatasource;
import com.gamewolf.database.dbsource.MysqlDataSource;

public class MySqlHandler extends DefaultJDBCDatasourceHandler implements IDatasourceHandler<MysqlDataSource>{
	
	MysqlDataSource datasource;

	@Override
	public void setDatasource(MysqlDataSource datasource) {
		super.datasource=datasource;
		this.datasource=datasource;
	}

	@Override
	public MysqlDataSource getDatasource() {
		return datasource;
	}


	@Override
	public boolean initialize() {
		boolean initializeFlag=false;
		ClientProxy proxy=ClientManager.getClient(datasource);
		if(proxy instanceof MysqlClientProxy){
			MysqlClientProxy mysqlClient=(MysqlClientProxy)proxy;
			this.template=new JdbcTemplate(mysqlClient.getMysqlConnectionSource());
			buildConfig();
			initializeFlag=true;
		}
		return initializeFlag;
	}

	@Override
	public void setTableDatasource(ITableDatasource tableDatasource) {
		// TODO Auto-generated method stub
		super.datasource=tableDatasource;
		this.datasource=(MysqlDataSource) tableDatasource;
	}

}
