package org.saatsch.framework.jmmo.tools.apiclient.ui.widgets;

public interface ConvertableToFromObject<T> {

  T asObject();

  /**
   * in this method, this object mutates it's state and after the mutation, is a representation of the
   * given object.
   * 
   * @param o 
   */
  void fromObject(T o);

}
