package com.gamewolf.database.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@PropertyConfiguration(propertieFile = "mysql.properties")
public @interface DefaultMysqlTableBinding {
	 
	 String table();
	 String javaClass();
}
