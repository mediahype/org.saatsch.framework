package org.saatsch.framework.jmmo.tools.apiclient.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * value object representing a {@link Method} on an {@link Object}.
 * 
 * @author saatsch
 *
 */
public class AbstractMethod {

  private final Object object;
  private final Method method;

  public AbstractMethod(Object object, Method method) {
    this.object = object;
    this.method = method;
  }
  
  /**
   * execute this {@link AbstractMethod} with the given args
   * 
   * @param args
   * @return
   */
  public Object execute(Collection<Object> args) {
    return invoke(args);
  }
  
  public String getDomainName() {
    return object.getClass().getSimpleName();
  }
  
  public String getName() {
    return method.getName();
  }
  
  public Class<?> getDeclaredIn() {
    return method.getDeclaringClass();
  }
  
  private Object invoke(Collection<Object> params) {
    try {
      return method.invoke(object, params.toArray());
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      
      e.printStackTrace();
    }
    return null;
  }
  
  public Method getMethod() {
    return method;
  }
  
}
