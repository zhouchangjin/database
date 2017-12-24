package com.gamewolf.database.dbconnector;

public class ConnectionMsg {
	
	public static String SUCCESS_NUM="100";
	
	String errorNum;
	String errorMsg;
	
	public String getErrorNum() {
		return errorNum;
	}
	public void setErrorNum(String errorNum) {
		this.errorNum = errorNum;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public String toString(){
		return errorNum+":"+errorMsg;
	}

}
