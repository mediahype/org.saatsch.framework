package de.jmmo.data.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Anntotate a {@link Bean} property that contains an object's name with this annotation. Multiple
 * properties of a {@link Bean} may be annotated, in which case the {@link #cardinality()} argument
 * of the annotation must vary. If multiple properties are annotated, the system forms the final
 * internal name of the Bean by concatenating the values of the annotated fields in ascending
 * order of their cardinality. Exactly one property annotated with this annotation must define
 * {@link #main()} as <code>true</code> and that property is regarded the external name of the
 * object - the name that can be presented to the end user. Note that neither internal nor external
 * name are regarded as properties that uniquely identify an object.
 * 
 * 
 * 
 * @author saatsch
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JmmoGivingName {

  int cardinality() default 2;

  /**
   * if <code>true</code> the annotated field represents the main name. The main name is suitable
   * for display to the application's end user.<br>
   * 
   * @return <code>true</code> if the annotated field represents the main name.
   */
  boolean main() default true;

}
