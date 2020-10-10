package org.saatsch.framework.jmmo.basegame.client;

import org.joda.beans.Bean;

import de.odysseus.el.util.SimpleContext;
import org.saatsch.framework.base.beans.change.BeanNamespace;
import org.saatsch.framework.base.expressions.Expressions;

/**
 * Implementation of {@link BeanNamespace}
 * 
 * @author saatsch
 *
 */

public class BeanNamespaceImpl extends Expressions implements BeanNamespace {

  public BeanNamespaceImpl() {
    context = new SimpleContext();
  }

  @Override
  public Bean getBean(String name) {
    // at this point we can only hope that it is a bean. Maybe we need an ObjectNamespace instead of the BeanNamespace
    return (Bean) evaluateToObject("#{" + name + "}") ;
  }

  @Override
  public void putBean(String name, Bean bean) {
    setVariable(name, bean);
    
  }

  @Override
  public <T> T getBean(Class<T> expected, String name) {
    return (T) evaluateToObject("#{" + name + "}") ;
  }

  // everything else is inherited.

}