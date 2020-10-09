package org.saatsch.framework.jmmo.data.api;

import java.io.Serializable;
import org.bson.types.ObjectId;

/**
 * A {@link MongoPointer} is a bit like annotating a property with @Reference except that it is
 * always lazily loaded. A MongoPointer is loaded when it is {@link #resolve()}d. As the database is
 * not available on the client, the client cannot resolve MongoPointers. Instead he uses them as
 * arguments to other server api calls.
 *
 * @param <T> the type of the object this pointer points to. Must be a mongo persitable type.
 */
public interface MongoPointer<T> extends Serializable {

  Class<?> getBaseClass();

  ObjectId getObjectId();

  /**
   * @param baseClass the base class, not null
   * @param objectId the objectId, not null
   */
  MongoPointer<T> setTargetCoodinate(Class<?> baseClass, ObjectId objectId);

  /**
   * resolves the object this {@link MongoPointer} points to.
   *
   * @return the resolved object or <code>null</code> if the object does not exist or the pointer
   * has no target.
   */
  T resolve();

  /**
   * if the MongoPointer is valid it should also be resolvable. If it is not resolvable, it is
   * considered a DataIntegrityException.
   */
  boolean isValid();

}
