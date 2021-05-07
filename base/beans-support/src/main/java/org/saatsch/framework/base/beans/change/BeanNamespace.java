package org.saatsch.framework.base.beans.change;

import org.joda.beans.Bean;

/**
 * describes a namespace of {@link Bean}s.
 */
public interface BeanNamespace {

  /**
   * gets a bean from the namespace
   * 
   * @param name the name of the bean
   * @return the Bean, not null.
   * @throws javax.el.PropertyNotFoundException when there was no bean for the given name
   */
  public Bean getBean(String name);

  /**
   * typesafe for {@link #getBean(String)}
   */
  public <T> T getBean(Class<T> expected, String name);
  
  /**
   * puts a bean into the namespace
   * 
   * @param name the name under which to put the bean
   * @param bean the bean
   */
  public void putBean(String name, Bean bean);


  
}
