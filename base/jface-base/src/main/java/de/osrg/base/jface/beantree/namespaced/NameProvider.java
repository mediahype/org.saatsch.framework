package de.osrg.base.jface.beantree.namespaced;

import java.util.Map.Entry;

import org.eclipse.jface.viewers.LabelProvider;
import org.joda.beans.Property;

import de.osrg.base.jface.beantree.BeanTreeUtil;

public class NameProvider extends LabelProvider {

  @Override
  public String getText(Object element) {

    StringBuilder sb = new StringBuilder();

    if (element instanceof Entry) {
      sb.append(((Entry) element).getKey()).append(" - ");
      Object value = ((Entry) element).getValue();
      sb.append(createText(value));

    } else {
      sb.append(createText(element));
    }

    return sb.toString();

  }

  private String createText(Object value) {
    if (value instanceof Property) {
      return ((Property<?>) value).name() + typeStringOf((Property<?>) value);
    }

    return "[" + value.getClass().getSimpleName() + "]";
  }

  private String typeStringOf(Property<?> prop) {

    if (BeanTreeUtil.isCollection(prop)) {
      return propTypeOf(prop) + typeArgOf(prop);
    } else {
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
