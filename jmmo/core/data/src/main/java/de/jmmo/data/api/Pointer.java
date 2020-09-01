package de.jmmo.data.api;

import java.io.Serializable;

import de.jmmo.data.annotations.JmmoAppId;


/**
 * Create Pointers with {@link PointerFactory}. Describes a pointer to a target
 * object. The pointer is a property of the source object. The property is not annotated with
 * {@link Reference}. The design ideas behind this indirection are that
 * <ul>
 * <li>even if the target object does not (yet) exist, the property of the source object can be
 * initialized to be not <code>null</code>.</li>
 * <li>when using Pointer - even if we have an Object cycle - we cannot run into
 * {@link StackOverflowError}s while calculating a hashCode.</li>
 * </ul>
 * 
 * Pointers can only point to Objects that have a {@link JmmoAppId}.
 * 
 * @author saatsch
 *
 * @param <T> the type of the target object to which this pointer points. This must (unfotunately as
 *        we do not yet have polymorphism established) always be a model base class.
 */
public interface Pointer<T> extends Serializable {


  Class<?> getBaseClass();

  /**
   * gets the appId of the object this pointer points to.
   * 
   * @return the appId of the object this pointer points to.
   */
  String getAppId();

  /**
   * sets the target of this {@link Pointer}
   * 
   * @param baseClass
   * @param appId
   * 
   * @return the pointer (fluent API).
   * 
   */
  Pointer<T> setTargetCoodinate(Class<?> baseClass, String appId);

  /**
   * resolves the target of this {@link Pointer} returning a sensible value even if the target does
   * not exist.
   * 
   * @return
   */
  String asString();
  
  /**
   * a Pointer is valid, if baseClass and appId are set to non null values.
   * A valid pointer is not neccessarily resolvable.
   * 
   * @return
   */
  boolean isValid();

}
