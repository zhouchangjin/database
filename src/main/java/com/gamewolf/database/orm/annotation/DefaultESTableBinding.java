
package com.gamewolf.database.orm.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
@PropertyConfiguration(propertieFile = "es.properties")
public @interface DefaultESTableBinding {
	
	String type();

}
