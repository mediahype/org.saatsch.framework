package de.osrg.base.jface.beantree.namespaced;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.joda.beans.Bean;
import org.joda.beans.Property;

import de.osrg.base.jface.beantree.BeanTreeUtil;


/**
 * @author saatsch
 *
 */
public class NamespaceContentProvider implements ITreeContentProvider {

  @Override
  public Object[] getElements(Object inputElement) {

    if (inputElement instanceof Map) {
      return ((Map) inputElement).entrySet().toArray();
    }
    
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

    if (element instanceof Entry) {
      Object value = ((Entry) element).getValue();
      return getChildrenOfObject(value);
    }
   
    return getChildrenOfObject(element);
    
  }

  private Object[] getChildrenOfObject(Object value) {
    if (value instanceof Property) {
      Property property = (Property) value;
      Object propertyValue = property.get();
      
      if (propertyValue instanceof Bean) {
        return propsOf((Bean) propertyValue).toArray();
      }
      if (BeanTreeUtil.isCollection(property)) {
        return ((Collection) propertyValue).toArray();  
      }
    }

    if (value instanceof Bean) {
      return getElements(value);
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

    if (element instanceof Entry) {
      Object value = ((Entry) element).getValue();
      return doesValueHaveChildren(value);
    }
    
    return doesValueHaveChildren(element);
    
  }

  private boolean doesValueHaveChildren(Object value) {

    if (value instanceof Property) {
      Object propertyValue = ((Property) value).get();
      if (propertyValue instanceof Bean) {
        return true;
      }
      if (isCollectionOfBeans((Property<Object>) value)) {
        return true;
      }
      
    }
    if (value instanceof Bean) {
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
