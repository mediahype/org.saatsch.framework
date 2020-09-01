package de.osrg.base.beans.change;

import org.joda.beans.Bean;

public class NamespaceChangeApplier {

  /**
   * applies a change to a bean in a namespace
   * 
   * @param change the change
   * @param namespace the namespace
   */
  public static void apply(PropertyChange change, BeanNamespace namespace) {
     
    Bean bean = namespace.getBean(change.getNameOfBean());
    ChangeApplier.apply(change, bean);
    
  }
  
}
