package com.gamewolf.database.orm.annotation.mapping;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(ElementType.METHOD)
public @interface UsingIndex {
	
	String analyzer() default "ik_max_word";
}
