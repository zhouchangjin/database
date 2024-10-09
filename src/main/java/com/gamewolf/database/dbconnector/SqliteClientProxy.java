package com.gamewolf.database.dbconnector;

import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.gamewolf.database.dbsource.AbstractDataSource;
import com.gamewolf.database.dbsource.SqliteDataSource;

public class SqliteClientProxy  extends ClientProxy{
	
	DataSource datasource;
	
	public DataSource getDatasource() {
		return datasource;
	}

	public SqliteClientProxy(AbstractDataSource abstractDatasource) {
		super(abstractDatasource);
		// TODO Auto-generated constructor stub
		if(abstractDatasource instanceof SqliteDataSource) {
			SqliteDataSource sqliteDataSource=(SqliteDataSource)abstractDatasource;
			DriverManagerDataSource driverManagerDatasouce=new DriverManagerDataSource();
			driverManagerDatasouce.setDriverClassName("org.sqlite.JDBC");
			driverManagerDatasouce.setUrl(sqliteDataSource.getConnectionStr());
			this.datasource=driverManagerDatasouce;
			
		}
	}

}
