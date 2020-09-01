package de.jmmo.data.impl;

import org.joda.beans.Bean;
import org.joda.beans.Property;

/**
 * a value object that holds a Bean and a Property.
 * @author saatsch
 *
 */
public class BeanAndProperty {

  private final Bean bean;
  private final Property<Object> prop;
  
  public BeanAndProperty(Bean bean, Property<Object> prop) {

    this.bean = bean;
    this.prop = prop;
  }

  public Bean getBean() {
    return bean;
  }

  public Property<Object> getProp() {
    return prop;
  }

  
}
