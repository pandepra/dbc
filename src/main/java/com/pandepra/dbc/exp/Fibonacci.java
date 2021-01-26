package com.pandepra.dbc.exp;

import com.pandepra.dbc.core.annotation.PostCondition;
import com.pandepra.dbc.core.annotation.PreCondition;
import com.pandepra.dbc.interceptors.Interceptor;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Default;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.ArrayList;
import java.util.List;

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
    List<Integer> l = new ArrayList<>();
    fibonacci.populateWithFibo(5, l);
  }

  @PreCondition(expression = "n > 0")
  @PostCondition(expression = "l.size() == 5")
  public void populateWithFibo(int n, List<Integer> l) {
    int n1 = 0, n2 = 1;
    l.add(n1);
    l.add(n2);
    for (int i = 1; i <= n - 2; i++) {
      int temp = n1 + n2;
      l.add(temp);
      n1 = n2;
      n2 = temp;
    }
    System.out.println(l.toString());
  }
}
