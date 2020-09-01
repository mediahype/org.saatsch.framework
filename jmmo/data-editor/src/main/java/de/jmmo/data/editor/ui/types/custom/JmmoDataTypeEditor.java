package de.jmmo.data.editor.ui.types.custom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.jmmo.data.editor.ui.types.AbstractEditorComposite;

/**
 * annotate classes that are custom editors for custom types (classes) with this annotation. Such
 * classes are
 * typically subclasses of {@link AbstractEditorComposite}.
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
