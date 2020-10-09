package org.saatsch.framework.jmmo.data.impl.visitor;

import static org.saatsch.framework.jmmo.data.api.PropertyUtil.isSupportedCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.beans.Bean;
import org.joda.beans.Property;

import org.saatsch.framework.jmmo.data.api.Pointer;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;

/**
 * Bean Visitor that also visits Beans inside supported collections of Beans and collects all Pointers it encounters.
 *  
 * 
 * @author saatsch
 *
 */
public class PointerCollector extends AbstractBeanVisitor {

  private List<Pointer> collect = new ArrayList<>();
  
  @Override
  protected boolean concreteVisit(Bean bean) {

    for (String propertyName : bean.propertyNames()) {
      Property<Object> property = bean.property(propertyName);

      // a pointer
      if (PropertyUtil.isPointer(property)) {
        collect.add((Pointer) property.get());
      }
      
      
      // a collection
      if (PropertyUtil.isSupportedCollection(property)){
        
        // of pointers
        if (PropertyUtil.isCollectionOfPointers(property)) {
          collect.addAll((Collection<Pointer>) property.get());
        }

        // of beans -> iterate and recurse
        if (Bean.class.isAssignableFrom(PropertyUtil.firstTypeArg(property.metaProperty()))) {
          for (Object nestedBean : (Collection) property.get()) {
            concreteVisit((Bean) nestedBean);
          }
        }
      }
    }
    
    return true;
    
  }

  public List<Pointer> getResult() {
    return collect;
  }
  
}
