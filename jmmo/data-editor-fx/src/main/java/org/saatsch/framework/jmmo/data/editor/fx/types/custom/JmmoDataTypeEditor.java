package org.saatsch.framework.jmmo.data.editor.fx.types.custom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.saatsch.framework.jmmo.data.editor.fx.types.AbstractEditor;

/**
 * annotate classes that are custom editors for custom types (classes) with this annotation. Such
 * classes are
 * typically subclasses of {@link AbstractEditor}.
 * 
 * @author saatsch
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JmmoDataTypeEditor {

  /**
   * @return the type this type-editor can edit.
   */
  Class<?> edits();

}
