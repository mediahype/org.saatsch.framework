package org.saatsch.framework.jmmo.data.impl.visitor;

import org.joda.beans.Bean;

/**
 * a {@link Bean} visitor that visits nested Beans (but not Beans that are inside nested Collections).
 * 
 * @author saatsch
 *
 */
public abstract class AbstractBeanVisitor {

  public void visit(Bean bean) {

    boolean shouldGoOn = concreteVisit(bean);

    if (shouldGoOn) {
      // iterate over properties
      for (String propName : bean.propertyNames()) {
        if ( null != bean.property(propName).get() && Bean.class.isAssignableFrom(bean.property(propName).get().getClass()) ) {
          // if property is a bean and not null then recurse.
          visit((Bean) bean.property(propName).get());

        }
      }
    }
  }

  /**
   * called when a Bean or nested Bean is visited.
   * 
   * @param bean the bean or nested Bean
   * @return if visiting should continue to recurse. <code>false</code> if visiting should stop for
   *         the current point in the branch.
   */
  protected abstract boolean concreteVisit(Bean bean);

}
