package org.saatsch.framework.jmmo.data.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Applied to Strings that want to be internationalizable by jmmo. When applied, the default is to have the
 * String be international and redered as one-liner in the editor.
 * 
 * @author saatsch
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JmmoString {

  /**
   * marks the string as either short or long. In the editor, short strings are rendered as Textbox
   * and long strings are rendered as Textarea (with multiple lines)
   * 
   * @return
   */
  StringStyle style() default StringStyle.SHORT;

  boolean intl() default true;

}
