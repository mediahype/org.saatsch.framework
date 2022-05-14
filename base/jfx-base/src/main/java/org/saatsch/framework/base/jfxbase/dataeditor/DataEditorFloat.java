package org.saatsch.framework.base.jfxbase.dataeditor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a {@link Float} field. This annotation is required. TODO: it should probably not be
 * required. This annotation and the given values are currently only used in the editor.
 * 
 * @author saatsch
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DataEditorFloat {

  float upperBound() default 100F;

  float lowerBound() default 0F;

  int precision() default 1;

  /**
   * used in the editor to set the increment of the spinner widget controlling the value of the
   * annotated property.
   */
  float increment() default 0.1F;

}
