package org.saatsch.framework.jmmo.data.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Fields annotated with this annotation are hidden in the Jmmo Data Editor. Fields annotated with
 * {@link JmmoAppId} do not require this annotation as they are implicitly hidden.
 * 
 * @author saatsch
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JmmoEditorHidden {

}
