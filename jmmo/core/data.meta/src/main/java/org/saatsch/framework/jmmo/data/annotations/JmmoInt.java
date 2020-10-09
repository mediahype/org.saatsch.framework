package org.saatsch.framework.jmmo.data.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * optionally applied to Interger properties. When applied it only gives a hint to what upper and
 * lower bound of the integer should be and those bounds are not enforced.
 * 
 * @author saatsch.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JmmoInt {

  int upperBound() default 100;

  int lowerBound() default 0;

}
