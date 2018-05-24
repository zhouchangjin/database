package com.gamewolf.database.orm.annotation;

public @interface MongoHandlerConfig {
	
	 String propertieFile() default "mongodb.properties";
	 String propertiePath() default "/";
	 boolean isResource() default true;
	 String collection();

}
