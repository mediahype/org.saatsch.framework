package org.saatsch.framework.jmmo.data.impl;

import org.saatsch.framework.jmmo.data.impl.visitor.AbstractBeanVisitor;

public interface BeanVisitable {

  void accept(AbstractBeanVisitor v);
  
}
