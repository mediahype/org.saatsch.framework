package org.saatsch.framework.jmmo.data.impl.visitor;

import java.util.ArrayList;
import java.util.List;

import org.joda.beans.Bean;

import org.saatsch.framework.jmmo.data.api.PropertyUtil;
import org.saatsch.framework.jmmo.data.impl.BeanAndProperty;

/**
 * a vistor that collects all intl string in a bean.
 * 
 * @author saatsch
 *
 */
public class IntlStringCollector extends AbstractBeanVisitor {

  private List<BeanAndProperty> collect = new ArrayList<>();
  
  
  @Override
  protected boolean concreteVisit(Bean bean) {
    
    for (String propertyName : bean.propertyNames()) {
      if (PropertyUtil.isIntlString(bean.property(propertyName))){
        collect.add(new BeanAndProperty(bean, bean.property(propertyName)));
      }
    }
    
    return true;
    
  }
  
  public List<BeanAndProperty> getResult() {
    return collect;
  }
  

}
