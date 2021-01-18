package com.pandepra.dbc.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A pre-condition can be put on a method to verify the validity of the input as dictated by {@link
 * PreCondition#expression()}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface PreCondition {
  String expression();
}
