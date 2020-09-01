package de.jmmo.data.editor.ui.util;

import org.joda.beans.Property;

/**
 * not yet in use.
 * 
 * @author saatsch
 *
 */
public class StringBuilderUtil {

  private StringBuilderUtil() {}

  public static String buildFor(Property<Object> property) {

    if (null == property.get()) {
      return "(Null)";
    }

    String propType = property.metaProperty().propertyType().toString();

    if (propType.contains("java.lang.Integer")) {
      return property.get().toString();
    }
    if (propType.contains("java.lang.String")) {
      return property.get().toString();
    }
    if (propType.contains("java.lang.Boolean")) {
      return property.get().toString();
    }
    if (propType.contains("java.lang.Float")) {
      return property.get().toString();
    }

    if (property.get().getClass().isEnum()) {
      return property.get().toString();
    }

    return "(unknown type)";

  }

}
