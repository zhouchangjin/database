package com.gamewolf.database.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface SqliteTableBinding {
	
	 String table();
	 Class javaClass();
	 String propertieFile() default "sqlite.properties";
	 String propertiePath() default "/";
	 boolean isResource() default true;

}
