package org.saatsch.framework.jmmo.data.editor.action;

import java.io.Serializable;

public class BeanPropertyChange {

  /**
   * the class of the bean this change applies to
   */
  private Class beanClass;
  
  /**
   * the id of the bean
   */
  private String id; 
  
  
  /**
   * 
   */
  private String propertyCoordinate;
  
  /**
   * the old value of the property
   */
  private Serializable oldValue;
  
  /**
   * the new value of the property
   */
  private Serializable newValue;
  
}
