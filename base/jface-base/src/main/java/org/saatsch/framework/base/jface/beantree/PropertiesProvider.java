package org.saatsch.framework.base.jface.beantree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.joda.beans.Bean;
import org.joda.beans.Property;


/**
 * @author saatsch
 *
 */
public class PropertiesProvider implements ITreeContentProvider {

  @Override
  public Object[] getElements(Object inputElement) {

    if (inputElement instanceof Bean) {
      Bean b = (Bean) inputElement;
      return propsOf(b).toArray();      
    }
    if (inputElement instanceof Collection) {
      return ((Collection) inputElement).toArray();
    }

    return new Object[0];
    
  }


  @Override
  public Object[] getChildren(Object element) {

    if (element instanceof Property) {
      Property property = (Property) element;
      Object propertyValue = property.get();
      
      if (propertyValue instanceof Bean) {
        return propsOf((Bean) propertyValue).toArray();
      }
      if (BeanTreeUtil.isCollection(property)) {
        return ((Collection) propertyValue).toArray();  
      }
    }

    if (element instanceof Bean) {
      return getElements(element);
    }

    return new Object[0];
  }

  @Override
  public Object getParent(Object element) {
    return null;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public boolean hasChildren(Object element) {

    if (element instanceof Property) {
      Object propertyValue = ((Property) element).get();
      if (propertyValue instanceof Bean) {
        return true;
      }
      if (isCollectionOfBeans((Property<Object>) element)) {
        return true;
      }
      
    }
    if (element instanceof Bean) {
      return true;
    }
    return false;
  }

  private List<Property> propsOf(Bean bean) {
    
    List<Property> ret = new ArrayList<>();
    for (String name : bean.propertyNames()) {
        ret.add(bean.property(name));
    }
    return ret;
    
  }

  
  private boolean isCollectionOfBeans(Property<Object> property) {
    return BeanTreeUtil.isCollection(property)
        && Bean.class.isAssignableFrom(BeanTreeUtil.firstTypeArg(property.metaProperty()));
  }
  
}
