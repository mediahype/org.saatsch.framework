package de.jmmo.data.api;

import org.joda.beans.Bean;

public class BeanReference {

  private final Bean bean;
  
  private final ReferenceDirection direction;

  public BeanReference(Bean bean, ReferenceDirection direction) {
    super();
    this.bean = bean;
    this.direction = direction;
  }

  public Bean getBean() {
    return bean;
  }

  public ReferenceDirection getDirection() {
    return direction;
  }
  
  
  
}
