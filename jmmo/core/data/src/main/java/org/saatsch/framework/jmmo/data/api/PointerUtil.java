package org.saatsch.framework.jmmo.data.api;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.joda.beans.Bean;
import org.joda.beans.MetaProperty;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.mongo.MorphiaMongoDataSink;
import dev.morphia.annotations.Id;

/**
 * Utility class to resolve targets of {@link Pointer}s. The database must be accessible in order for
 * this to work.
 *
 */
public class PointerUtil {

  private PointerUtil() {}

  
  public static <T> T resolve(Pointer<T> pointer) {
    requireNonNull(pointer);
    String appIdPropertyName =
        PropertyUtil.getAppIdPropertyName((Class<? extends Bean>) pointer.getBaseClass());

    requireNonNull(appIdPropertyName,
        "Pointer must point to a class that has a JmmoAppId defined, but " + pointer.getBaseClass()
            + " has none.");



    MorphiaMongoDataSink dataSink = JmmoContext.getBean(MorphiaMongoDataSink.class);
    T object = null;
    if (dataSink.isInitialized()) {
      object = (T) dataSink.store().createQuery(pointer.getBaseClass())
          .filter(appIdPropertyName + " = ", pointer.getAppId()).first();

    }

    return object;

  }

  
  
  /**
   * returns the target of the given pointer or an empty Optional if the target does not exist.
   * Attention:
   * this method does a database call.
   *
   * @param pointer the pointer, not null
   * @return the target of the given pointer or an empty Optional if the target does not exist.
   */
  public static <T> Optional<T> resolveOptional(Pointer<T> pointer) {
    requireNonNull(pointer);
    String appIdPropertyName =
        PropertyUtil.getAppIdPropertyName((Class<? extends Bean>) pointer.getBaseClass());

    requireNonNull(appIdPropertyName,
        "Pointer must point to a class that has a JmmoAppId defined, but " + pointer.getBaseClass()
            + " has none.");



    MorphiaMongoDataSink dataSink = JmmoContext.getBean(MorphiaMongoDataSink.class);
    T object = null;
    if (dataSink.isInitialized()) {
      object = (T) dataSink.store().createQuery(pointer.getBaseClass())
          .filter(appIdPropertyName + " = ", pointer.getAppId()).get();

    }

    return Optional.ofNullable(object);

  }

  /**
   * calls {@link #resolveOptional(Pointer)} for each pointer in a given list.
   * 
   * @param list the List of Pointers to resolve
   * @return the List of resolved Objects. If all Pointers were resolved successfully, the returned
   *         List has the same size as the input.
   */
  public static <T> List<T> resolve(List<Pointer<T>> list) {
    List<T> ret = new ArrayList<>();
    list.forEach(p -> resolveOptional(p).ifPresent(ret::add));
    return ret;
  }

  @SuppressWarnings("unchecked")
  public static <T> T resolve(MongoPointer<T> pointer) {
    if (pointer.getBaseClass() == null) {
      return null;
    }

    MetaProperty<?> idProperty = PropertyUtil
        .getPropertyAnnotatedWith((Class<? extends Bean>) pointer.getBaseClass(), Id.class);

    T object = (T) JmmoContext.getBean(MorphiaMongoDataSink.class).store()
        .createQuery(pointer.getBaseClass())
        .filter(idProperty.name() + " = ", pointer.getObjectId()).get();

    return object;
  }

}
