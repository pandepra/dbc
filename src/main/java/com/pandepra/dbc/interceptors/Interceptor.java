package com.pandepra.dbc.interceptors;

import com.pandepra.dbc.core.annotation.PostCondition;
import com.pandepra.dbc.core.annotation.PreCondition;
import com.pandepra.dbc.core.exception.PreConditionViolationException;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.concurrent.Callable;

public class Interceptor {

  private static final JexlEngine JEXL =
      new JexlBuilder().cache(512).strict(true).silent(false).create();

  @RuntimeType
  public void interceptForPreCondition(
      @Origin Method method, @AllArguments Object[] args, @SuperCall Callable<Void> zuper)
      throws Exception {
    PreCondition annotation = method.getAnnotation(PreCondition.class);
    JexlExpression expression = JEXL.createExpression(annotation.expression());
    JexlContext context = new MapContext();
    Parameter[] parameters = method.getParameters();
    int i = 0;
    for (Parameter parameter : parameters) {
      String name = parameter.getName();
      context.set(name, args[i++]);
    }
    if (!((boolean) expression.evaluate(context))) {
      throw new PreConditionViolationException("Pre-condition violation");
    }
    zuper.call();
    expression = JEXL.createExpression(method.getAnnotation(PostCondition.class).expression());
    if (!((boolean) expression.evaluate(context))) {
      throw new PreConditionViolationException("Post-condition violation");
    }
  }
}
