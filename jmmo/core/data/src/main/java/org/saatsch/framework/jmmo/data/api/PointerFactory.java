package org.saatsch.framework.jmmo.data.api;

import java.lang.reflect.InvocationTargetException;

import org.bson.types.ObjectId;
import org.joda.beans.Bean;
import org.joda.beans.MetaProperty;

import org.saatsch.framework.jmmo.data.DataException;
import org.saatsch.framework.jmmo.data.annotations.JmmoAppId;
import org.saatsch.framework.jmmo.data.impl.MongoPointerImpl;
import org.saatsch.framework.base.util.Assert;
import dev.morphia.annotations.Id;

public class PointerFactory {

  private static final String POINTER_IMPL = "org.saatsch.framework.jmmo.data.impl.PointerImpl";
  private static final String MONGO_POINTER_IMPL = "org.saatsch.framework.jmmo.data.impl.MongoPointerImpl";

  private PointerFactory() {}

  /**
   * creates a new Pointer. The created Pointer is empty and thus invalid but not null. This is used
   * for creating default values in model classes where the concrete object to point to is not yet
   * known.
   * 
   * @param pointerClass used for type safety.
   * @return the new empty Pointer
   */
  public static <T> Pointer<T> newPointer(Class<T> pointerClass) {

    try {
      return (Pointer<T>) Class.forName(POINTER_IMPL).getDeclaredConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException
        | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
        | SecurityException e) {
      throw new DataException("failed to create an instance of " + POINTER_IMPL, e);
    }

  }


  /**
   * creates a new Pointer and lets it point to the given target {@link Bean}.
   * 
   * @param target the target Bean of the Pointer. The Bean must have a {@link JmmoAppId} annotated
   *        property. Must not be <code>null</code>.
   * @return the new Pointer
   * @throws IllegalArgumentException if the target was not a Bean or was <code>null</code>
   */
  public static Pointer newPointer(Bean target) {
    Assert.notNull(target);
    Assert.notNull(PropertyUtil.getAppId(target));

    try {
      Pointer newInstance = (Pointer) Class.forName(POINTER_IMPL).getDeclaredConstructor().newInstance();
      newInstance.setTargetCoodinate(target.metaBean().beanType(), PropertyUtil.getAppId(target));
      return newInstance;
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException
        | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 
   * 
   * @param beanClass
   * @param target
   * @return
   */
  @SuppressWarnings("unchecked")
  public static <T> Pointer<T> newPointer(Class<T> beanClass, Bean target) {
    Assert.notNull(PropertyUtil.getAppId(target));

    try {
      Pointer<T> newInstance =
          (Pointer<T>) Class.forName(POINTER_IMPL).getDeclaredConstructor().newInstance();
      newInstance.setTargetCoodinate(beanClass, PropertyUtil.getAppId(target));
      return newInstance;
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException
        | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
        | SecurityException e) {
      throw new RuntimeException(e);
    }

  }

  /**
   * @param pointerClass for type safety
   * @return a new, empty {@link MongoPointer}.
   */
  public static <T> MongoPointer<T> newMongoPointer(Class<T> pointerClass) {
    try {
      return (MongoPointer<T>) Class.forName(MONGO_POINTER_IMPL).getDeclaredConstructor()
          .newInstance();
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException
        | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
        | SecurityException e) {
      throw new DataException("failed to create an instance of " + POINTER_IMPL, e);
    }
  }

  /**
   * @param beanClass for type safety
   * @param target the target of the Pointer
   * @return the new Pointer
   */
  public static <T> MongoPointer<T> newMongoPointer(Class<T> beanClass, Bean target) {

    MetaProperty<?> metaProperty = PropertyUtil.getPropertyAnnotatedWith(beanClass, Id.class);

    try {
      MongoPointer<T> newInstance =
          (MongoPointerImpl<T>) Class.forName(MONGO_POINTER_IMPL).getDeclaredConstructor().newInstance();
      newInstance.setTargetCoodinate(target.metaBean().beanType(),
          (ObjectId) metaProperty.get(target));
      return newInstance;
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
      throw new DataException("failed to create an instance of " + POINTER_IMPL, e);
    }

  }

}
