package de.jmmo.data.api;

import java.util.Set;

import org.joda.beans.Bean;


/**
 * value object that contains incoming/outgoing references to/from a Bean.
 * 
 * @author saatsch
 *
 */
public class References {

  /**
   * the Bean to/from which the references are incoming/outgoing
   */
  private final Bean bean;
  
  /**
   * the {@link BeanReference}s.
   */
  private final Set<BeanReference> refs;

  public References(Bean bean, Set<BeanReference> refs) {
    super();
    this.bean = bean;
    this.refs = refs;
  }

  public Bean getBean() {
    return bean;
  }

  public Object[] toArray() {
    return refs.toArray();
  }
  
}
