package org.saatsch.framework.jmmo.data.editor.fx.eventing;

import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.joda.beans.gen.BeanDefinition;

/**
 * represents the event in which the name of a Bean has changed.
 */
public class NameChanged {

  private final Bean objectToEdit;
  private final Property<Object> property;

  public NameChanged(Property<Object> property, Bean objectToEdit) {
    this.property = property;
    this.objectToEdit = objectToEdit;
  }

  public Bean getObjectToEdit() {
    return objectToEdit;
  }

  public Property<Object> getProperty() {
    return property;
  }
}
