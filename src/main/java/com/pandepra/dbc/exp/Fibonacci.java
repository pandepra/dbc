package com.pandepra.dbc.exp;

import com.pandepra.dbc.interceptors.Interceptor;
import com.pandepra.dbc.core.annotation.PreCondition;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Default;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

public class Fibonacci {

  public static void main(String[] args) throws IllegalAccessException, InstantiationException {
    Fibonacci fibonacci =
        new ByteBuddy()
            .subclass(Fibonacci.class)
            .method(ElementMatchers.isAnnotatedWith(PreCondition.class))
            .intercept(MethodDelegation.to(new Interceptor()))
            .make()
            .load(Fibonacci.class.getClassLoader(), Default.WRAPPER)
            .getLoaded()
            .newInstance();
    fibonacci.printFibo(7, 3);
  }

  @PreCondition(expression = "i > 5 && j < 2")
  public void printFibo(int i, int j) {
    System.out.println("Here");
  }
}
