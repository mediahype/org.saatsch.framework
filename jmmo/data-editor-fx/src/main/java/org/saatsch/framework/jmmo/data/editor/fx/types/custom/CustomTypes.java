package org.saatsch.framework.jmmo.data.editor.fx.types.custom;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.reflections8.Reflections;
import org.saatsch.framework.jmmo.data.editor.fx.types.AbstractEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * collector for custom types. It works on classes annotated with {@link JmmoDataTypeEditor}.
 * 
 * @author saatsch
 *
 */

public class CustomTypes {
  private static final Logger LOG = LoggerFactory.getLogger(CustomTypes.class);
  private static final String PACKAGE = "org.saatsch.framework.jmmo.data.editor.fx.custom";
  private Set<Class<?>> types;

  /**
   * key of this map is the custom class that gets edited, value is the custom editor that is annotated with {@link JmmoDataTypeEditor}
   */
  private Map<Class<?>, Class<?>> typeMappings = new HashMap<>();

  public CustomTypes() {
    init();
  }
  

  private void init() {

    Reflections reflections = new Reflections(PACKAGE);

    types = reflections.getTypesAnnotatedWith(JmmoDataTypeEditor.class);

    for (Class<?> c : types) {
      checkContract(c);
    }
    for (Class<?> c : types) {
      buildTypeMapping(c);
    }

  }


  private void buildTypeMapping(Class<?> c) {
    typeMappings.put(c.getAnnotation(JmmoDataTypeEditor.class).edits(), c);
  }


  private void checkContract(Class<?> type) {
    // TODO: check contract
  }


  public boolean contains(Class<? extends Object> c) {
    for (Class<?> clazz : types) {
      if (clazz.getAnnotation(JmmoDataTypeEditor.class).edits().equals(c)) {
        return true;
      }
    }

    return false;
  }

  public Class<?> getEditorFor(Class<? extends Object> c) {
    return typeMappings.get(c);
  }

  /**
   * instantiate a custom editor component.
   * 
   * @param c class of the object that should be edited.
   * @param parent
   * @param property
   * @param style
   * @param objectToEdit
   * @return
   */
  public AbstractEditor instantiateEditorFor(Class<?> c, 
      Property<Object> property,  Bean objectToEdit) {

    Class<?> editorClass = typeMappings.get(c);

    Constructor<?> constructor = null;
    try {
      constructor =
          editorClass.getConstructor(Property.class, Bean.class);
    } catch (NoSuchMethodException | SecurityException e) {
      LOG.error("constructor not found:", e);
      return null;
    }

    Object newInstance = null;

    try {
      newInstance = constructor.newInstance( property, objectToEdit);
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException e) {
      LOG.error("while invoking constructor:", e);
      return null;
    }

    return (AbstractEditor) newInstance;

  }

}
