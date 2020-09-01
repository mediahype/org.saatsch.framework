package de.jmmo.data.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotate the Bean property that contains an application specific id with this annotation. The
 * annotated field <b>must</b> be a String. JMMO does not enforce uniqueness of the given id by
 * itself. Instead, it is assumed that a unique index on the annotated property is established in
 * the database.
 * 
 * 
 * @author saatsch
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)

public @interface JmmoAppId {

}
