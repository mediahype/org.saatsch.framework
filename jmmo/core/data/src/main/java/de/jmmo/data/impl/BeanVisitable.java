package de.jmmo.data.impl;

import de.jmmo.data.impl.visitor.AbstractBeanVisitor;

public interface BeanVisitable {

  void accept(AbstractBeanVisitor v);
  
}
