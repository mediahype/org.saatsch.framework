package de.jmmo.data.annotations.runtime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * this annotation marks properties that are Beans and are also annotated with mongo's @Reference
 * annotation for <em>exclusion</em> from the depth delete operation. The depth delete operation is a
 * convenience method provided by jmmo that - given a Bean - visits nested Beans that are annotated
 * with @Reference and does a depth first delete on any Bean it finds.
 * 
 * @author saatsch
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DepthDeleteStop {

}
