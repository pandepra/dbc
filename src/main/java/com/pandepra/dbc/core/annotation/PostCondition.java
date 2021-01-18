package com.pandepra.dbc.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A post-condition can be put on a method to verify the validity of the input as dictated by {@link
 * PostCondition#expression()}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface PostCondition {
  String expression();
}
