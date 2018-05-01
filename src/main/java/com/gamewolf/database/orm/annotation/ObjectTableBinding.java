package com.gamewolf.database.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ObjectTableBinding {
	
	 String table();
	 Class javaClass();
	 String propertieFile();
	 String propertiePath();
	 boolean isResource();
		
}
