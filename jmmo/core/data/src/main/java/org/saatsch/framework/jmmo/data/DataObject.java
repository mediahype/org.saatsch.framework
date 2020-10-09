package org.saatsch.framework.jmmo.data;

import java.io.Serializable;

/**
 * An abstract data object. This is the superclass of user model classes that wish to be persisted.
 * This class only has one field - id - which is governed by the database. It is recommended that
 * application objects define an additional unique application key. Only in that way application
 * classes could be migrated from one database implementation to another (exchanging this class in
 * the process). <br>
 * 
 * Calling the {@link #getId()} method of this class is discouraged! Instead every {@link Object} in
 * the user data model should have an appId field by which the application uniquely identifies an
 * object. TODO: explain
 * 
 * @author saatsch
 *
 */
public interface DataObject<T> extends Serializable {

  T getId();

}
