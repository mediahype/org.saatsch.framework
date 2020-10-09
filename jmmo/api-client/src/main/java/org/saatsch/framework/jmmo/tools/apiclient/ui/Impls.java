package org.saatsch.framework.jmmo.tools.apiclient.ui;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;

/**
 * Singleton pattern helper class that knows which classes shall be handled by the UI.
 * 
 * @author saatsch
 *
 */
public class Impls {

  private final List<AbstractMethod> me = new ArrayList<>();


  private static final class InstanceHolder {
    static final Impls INSTANCE = new Impls();
  }

  private Impls() {
    init();
  }

  public static Impls getInstance() {
    return InstanceHolder.INSTANCE;
  }

  public List<AbstractMethod> getTasks() {
    return me;
  }

  /**
   * @param name
   * @return the task or <code>null</code>
   */
  public AbstractMethod getTask(String name) {
    for (AbstractMethod at : me) {
      if (name.equals(at.getClass().getSimpleName())) {
        return at;
      }
    }
    return null;
  }

  private void init() {


    

  }

  public Impls create(Class clazz) {

    for (Method method : clazz.getMethods()) {
      if (method.getDeclaringClass() != Object.class) {
        AbstractMethod at = new AbstractMethod(JmmoContext.getBean(clazz), method);
        me.add(at);
      }
    }
    
    return this;
    
  }

}
