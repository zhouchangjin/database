package com.gamewolf.database.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface PropertyConfiguration {
	String propertieFile();
	String propertiePath() default "/";
	boolean isResource() default true;
}
