package com.gamewolf.database.dbconnector;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import com.gamewolf.database.dbsource.AbstractDataSource;
import com.gamewolf.database.dbsource.MysqlDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;


public class MysqlClientProxy extends ClientProxy{
	
	public DataSource getMysqlConnectionSource() {
		return mysqlConnectionSource;
	}


	DataSource mysqlConnectionSource;

	public MysqlClientProxy(AbstractDataSource abstractDatasource) {
		super(abstractDatasource);
		// TODO Auto-generated constructor stub
		if(abstractDatasource instanceof MysqlDataSource){
			MysqlDataSource datasource=(MysqlDataSource)abstractDatasource;
			ComboPooledDataSource c3p0datasource=new ComboPooledDataSource();
			String driverClass="com.mysql.jdbc.Driver";
			String jdbcUrl=datasource.getConnectionStr();
			String username=datasource.getUsername();
			String pwd=datasource.getPassword();
			
			int initPoolSize=10;
			int minPoolSize=10;
			int maxPoolSize=1000;
			int acquireIncrement=10;
			int maxIdleTime=1200;
			int maxConnectionAge=0;
			int idleConnectionTestPeriod=300;
			boolean testConnectionOnCheckin=false;
			boolean testConnectionOnCheckout=false;
			int acquireRetryAttempts=2;
			int acquireRetryDelay=10000;
			int checkoutTimeout=5000;
			int maxStatements= 0;
			try {
				c3p0datasource.setDriverClass(driverClass);
			} catch (PropertyVetoException e) {
				
				e.printStackTrace();
			}
		
			c3p0datasource.setJdbcUrl(jdbcUrl);
			c3p0datasource.setUser(username);
			c3p0datasource.setPassword(pwd);
			c3p0datasource.setInitialPoolSize(initPoolSize);
			c3p0datasource.setMaxPoolSize(maxPoolSize);
			c3p0datasource.setMinPoolSize(minPoolSize);
			c3p0datasource.setAcquireIncrement(acquireIncrement);
			c3p0datasource.setMaxIdleTime(maxIdleTime);
			c3p0datasource.setMaxConnectionAge(maxConnectionAge);
			c3p0datasource.setIdleConnectionTestPeriod(idleConnectionTestPeriod);
			c3p0datasource.setTestConnectionOnCheckin(testConnectionOnCheckin);
			c3p0datasource.setTestConnectionOnCheckout(testConnectionOnCheckout);
			c3p0datasource.setAcquireRetryAttempts(acquireRetryAttempts);
			c3p0datasource.setAcquireRetryDelay(acquireRetryDelay);
			c3p0datasource.setCheckoutTimeout(checkoutTimeout);
			c3p0datasource.setMaxStatements(maxStatements);
			this.mysqlConnectionSource=c3p0datasource;
			
		}else{
			
		}
		
	}
	
	


}
