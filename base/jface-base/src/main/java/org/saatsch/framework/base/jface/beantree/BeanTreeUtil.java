package org.saatsch.framework.base.jface.beantree;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import org.joda.beans.MetaProperty;
import org.joda.beans.Property;

public class BeanTreeUtil {

  public static boolean isCollection(Property prop) {
    return Collection.class.isAssignableFrom(prop.metaProperty().propertyType());
  }
  
  /**
   * returns the first type argument of a property or <code>null</code> if the given property is not
   * a parameterized type or if it is parameterized with a wildcard (e.g. ? extends Bean).
   * 
   * @param metaProperty the (description of the) property.
   * @return
   */
  public static Class<?> firstTypeArg(MetaProperty<?> metaProperty) {

    ParameterizedType type;
    try {
      type = (ParameterizedType) metaProperty.propertyGenericType();
    } catch (ClassCastException e) {
      return null;
    }

    try {
      return (Class<?>) type.getActualTypeArguments()[0];
    } catch (ClassCastException e) {

      Type t = type.getActualTypeArguments()[0];
      
      if (t instanceof ParameterizedType) {
        type = (ParameterizedType) type.getActualTypeArguments()[0];
        return (Class<?>) type.getRawType();        
      }
      
      return null;

    }
  }
  
}
