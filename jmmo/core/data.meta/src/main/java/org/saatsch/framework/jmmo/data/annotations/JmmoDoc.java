package org.saatsch.framework.jmmo.data.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate fields with this annotation if you want to give a documenting text for that field. The
 * text will be displayed in the Jmmo Data Editor.
 * 
 * @author saatsch
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JmmoDoc {

  String value();

}
