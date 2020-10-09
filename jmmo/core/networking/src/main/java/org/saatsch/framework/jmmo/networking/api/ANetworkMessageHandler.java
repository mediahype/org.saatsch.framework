package org.saatsch.framework.jmmo.networking.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotate classes that want to be informed of typed network messages with this
 * annotation. Such classes <em>must always</em> also implement
 * {@link NetworkMessageHandler}. See the documentation of
 * {@link NetworkMessageHandler} for further details.
 * 
 * The annotation attribute {@link #listenTo()} gives you the opportunity to
 * specify an array of types to listen for.
 * 
 * 
 * 
 * @author saatsch
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ANetworkMessageHandler {

	Class<?>[] listenTo();

}
