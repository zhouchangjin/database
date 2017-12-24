package com.gamewolf.database.dbsource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.gamewolf.database.dbconnector.ConnectionMsg;
import com.gamewolf.database.dbsource.AbstractDataSource;
/**
 * 
 * @author
 *
 */
public class MysqlDataSource extends AbstractDataSource {
	
	String host;
	Integer port;
	String username;
	String password;
	String database;
	String table;

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

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public ConnectionMsg testConnection() {
		// TODO Auto-generated method stub
	
		ConnectionMsg msg=new ConnectionMsg();
		Connection con=null;
		try {
			System.out.println("comming");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url=getConnectionStr();
			System.out.println("comming"+url);
			con = DriverManager.getConnection(url);
			System.out.println("comming"+url);
			boolean test=con.isValid(10);
			if(test){
				msg.setErrorNum(ConnectionMsg.SUCCESS_NUM);
				msg.setErrorMsg("connection successfull");
			}else{
				msg.setErrorNum(""+99);
				msg.setErrorMsg("connection failed");
			}
		} catch (SQLException e) {
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
		String url="jdbc:mysql://"+this.host+":"+this.port+"/"+this.database+"?user="+this.username+"&password="+this.password+"&zeroDateTimeBehavior=convertToNull&autoReconnect=true&autoReconnectForPools=true&useUnicode=true&characterEncoding=utf-8";
		return url;
	}

}
