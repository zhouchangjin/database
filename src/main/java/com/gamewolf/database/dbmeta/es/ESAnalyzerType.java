package com.gamewolf.database.dbmeta.es;

public enum ESAnalyzerType {
	IK("ik"),
	SMARTCN("samrtcn");
	public final String analyzerType;
	ESAnalyzerType(String analyzertype){
		this.analyzerType=analyzertype;
	}
	
	public String toString(){
		return analyzerType;
	}

}