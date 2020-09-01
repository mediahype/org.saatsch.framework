package de.jmmo.data;

import java.util.Comparator;
import java.util.NoSuchElementException;

import org.joda.beans.Property;

import de.jmmo.data.annotations.JmmoGivingName;

/**
 * compares 2 properties by the cardinality of their {@link JmmoGivingName} annotation.
 * Said annotation must be present on both properties. If this is not the case, a {@link NoSuchElementException} is thrown. 
 * 
 * @author saatsch
 *
 */
public class GivingNameComparator implements Comparator<Property<Object>> {

  @Override
  public int compare(Property<Object> prop1, Property<Object> prop2) {

    int int1 = prop1.metaProperty().annotation(JmmoGivingName.class).cardinality();
    int int2 = prop2.metaProperty().annotation(JmmoGivingName.class).cardinality();

    return Integer.compare(int1, int2);
    
    

  }

}
