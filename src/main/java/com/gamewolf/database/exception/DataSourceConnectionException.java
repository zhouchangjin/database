package com.gamewolf.database.exception;

import com.gamewolf.database.dbconnector.ConnectionMsg;

public class DataSourceConnectionException extends BaseException {
	
	private static final long serialVersionUID = 1L;

	public DataSourceConnectionException(ConnectionMsg msg) {
		// TODO Auto-generated constructor stub
		super(msg.toString());
	}

}
