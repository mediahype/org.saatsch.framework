package de.jmmo.data.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Add this annotation to a Jmmo managed base class if you want to enable categorizing content into
 * folders in the editor. If this annotation is applied to a non base class, it is ignored. 
 * 
 * the value(s) given to this annotation determines how the class is categorized. The order of values is relevant. 
 * 
 * @author saatsch
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface JmmoCategories {

  Category[] value();

}
