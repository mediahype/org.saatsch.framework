package de.jmmo.data.impl.visitor;

import static de.jmmo.data.api.PropertyUtil.firstTypeArg;
import static de.jmmo.data.api.PropertyUtil.isCollectionOfPointers;
import static de.jmmo.data.api.PropertyUtil.isPointer;
import static de.jmmo.data.api.PropertyUtil.isSupportedCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.beans.Bean;
import org.joda.beans.Property;

import de.jmmo.data.api.Pointer;

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
      if (isPointer(property)) {   
        collect.add((Pointer) property.get());
      }
      
      
      // a collection
      if (isSupportedCollection(property)){
        
        // of pointers
        if (isCollectionOfPointers(property)) {
          collect.addAll((Collection<Pointer>) property.get());
        }

        // of beans -> iterate and recurse
        if (Bean.class.isAssignableFrom(firstTypeArg(property.metaProperty()))) {
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
