package com.gamewolf.database.dbsource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.gamewolf.database.dbconnector.ConnectionMsg;

public class SqliteDataSource extends AbstractDataSource implements ITableDatasource{
	
	String databaseFile;
	String table;

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getDatabaseFile() {
		return databaseFile;
	}

	public void setDatabaseFile(String databaseFile) {
		this.databaseFile = databaseFile;
	}

	@Override
	public ConnectionMsg testConnection() {
		ConnectionMsg msg=new ConnectionMsg();
		Connection con=null;
		try {
			Class.forName("org.sqlite.JDBC").newInstance();
			String url=getConnectionStr();
			con = DriverManager.getConnection(url);
			boolean test=con.isValid(10);
			if(test){
				msg.setErrorNum(ConnectionMsg.SUCCESS_NUM);
				msg.setErrorMsg("connection successfull");
			}else{
				msg.setErrorNum(""+99);
				msg.setErrorMsg("connection failed");
			}
		}catch (SQLException e) {
			msg.setErrorNum(""+98);
			msg.setErrorMsg("SQL Exception occurrs");
			e.printStackTrace();
		} catch (InstantiationException e) {
			msg.setErrorNum(""+97);
			msg.setErrorMsg("InstantiationException Exception occurrs");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			msg.setErrorNum(""+96);
			msg.setErrorMsg("IllegalAccessException Exception occurrs");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			msg.setErrorNum(""+95);
			msg.setErrorMsg("ClassNotFoundException Exception occurrs");
			e.printStackTrace();
		}finally{
			if(con!=null){
				try{
					con.close();
				}catch(Exception e1){
					
				}
			}
		}
		return msg;
	}

	@Override
	public String getConnectionStr() {
		// TODO Auto-generated method stub
		return "jdbc:sqlite:"+databaseFile;
	}

}
