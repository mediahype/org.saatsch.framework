package org.saatsch.framework.jmmo.data.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * describes one of (possibly multiple) categorizations that can be applied to a jmmo base class.
 * By default it categorizes by specialization class. 
 * 
 * @author saatsch
 *
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface Category {

  /**
   * the category type. If {@link CategoryType#CLASS} then {@link #propertyName()} is ignored, if
   * {@link CategoryType#PROPERTY} the {@link #propertyName()} must not be empty. Default is CLASS.
   * 
   * @return the category type.
   */
  CategoryType type() default CategoryType.CLASS;

  /**
   * the name of the property that should categorize instances of the annotated class. The property
   * must be an Enum. This is ignored if {@link #type()} is CLASS.
   * 
   * @return the name of the property that should categorize instances of the annotated class.
   */ 
  String propertyName() default "";

}
