package org.saatsch.framework.base.jface.beantree;

import org.eclipse.jface.viewers.LabelProvider;
import org.joda.beans.Property;

public class NameProvider extends LabelProvider {

  @Override
  public String getText(Object element) {

    if (element instanceof Property) {
      return ((Property<?>) element).name() + typeStringOf((Property<?>) element) ;
    }

    
    return "[" + element.getClass().getSimpleName() + "]";

  }

  
  private String typeStringOf(Property<?> prop) {
    
    if (BeanTreeUtil.isCollection(prop)) {
      return propTypeOf(prop) + typeArgOf(prop);
    }
    else {
      return propTypeOf(prop);
    }
    
  }
  
  private String propTypeOf(Property<?> prop) {
    return " [" + prop.metaProperty().propertyType().getSimpleName() + "]";
  }
  
  private String typeArgOf(Property<?> prop) {
    return "<" + BeanTreeUtil.firstTypeArg(prop.metaProperty()).getSimpleName() + ">";
  }
  
}
