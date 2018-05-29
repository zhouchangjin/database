package com.gamewolf.database.dbmeta.es;

public enum ESFieldType {
	
	String("string"),
	Integer("integer"),
	Double("double"),
	Long("long"),
	Date("date"),
	Float("float"),
	Object("object");
	
	private final String type;
	
	
	 ESFieldType(String type ){
		this.type=type;
	}
	 
	 public String toString(){
		 return type;
	 }


}