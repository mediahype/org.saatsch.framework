package org.saatsch.framework.base.jfxbase.dataeditor;

import org.joda.beans.Bean;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

public class PropertyUtil {

  /**
   * returns <code>true</code> if the property is annotated with the given annotation class,
   * <code>false</code> if the given property is not annotated with the given annotation or the
   * given property was <code>null</code>.
   */
  public static boolean isPropertyAnnotatedWith(Property<Object> property, Class annotation) {

    if (null == property) {
      return false;
    }

    boolean annotationPresent = false;
    Annotation ann = null;
    try {
      ann = property.metaProperty().annotation(annotation);
    } catch (NoSuchElementException e) {
    }
    if (null != ann) {
      annotationPresent = true;
    }
    return annotationPresent;
  }

  public static boolean isHidden(MetaProperty<?> property){
    if (isPropertyAnnotatedWith(property, DataEditor.class)){
      return property.annotation(DataEditor.class).hidden();
    }
    return false;
  }


  public static boolean isPropertyAnnotatedWith(MetaProperty<?> metaProperty, Class annotation) {

    if (null == metaProperty) {
      return false;
    }

    boolean annotationPresent = false;
    Annotation ann = null;
    try {
      ann = metaProperty.annotation(annotation);
    } catch (NoSuchElementException e) {
    }
    if (null != ann) {
      annotationPresent = true;
    }
    return annotationPresent;
  }

  public static <T extends Annotation> Optional<T> propertyAnnotation(MetaProperty<?> metaProperty, Class<T> annotation) {
    if (null == metaProperty) {
      return Optional.empty();
    }
    T ann = null;
    try {
      ann = metaProperty.annotation(annotation);
    } catch (NoSuchElementException e) {
    }
    return Optional.ofNullable(ann);
  }


  /**
   * creates a new Instance of a given a Bean class using the no-arg constructor.
   *
   * @param c the Bean class
   * @return the new instance
   * @throws RuntimeException if no instance could be created.
   */
  public static Bean newInstance(Class<? extends Bean> c) {

    Bean newInstance = null;
    try {
      newInstance = c.getConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException | NoSuchMethodException | SecurityException e) {
      throw new RuntimeException("Could not create instance of " + c.getName(), e);
    }

    return newInstance;
  }

  /**
   * returns the first type argument of a property or <code>null</code> if the given property is not
   * a parameterized type. Recurses, if said first type argument is a parameterized type again.
   *
   * @param metaProperty the (description of the) property.
   */
  public static Class<?> firstTypeArgRecurse(MetaProperty<?> metaProperty) {

    ParameterizedType type = null;
    try {
      type = (ParameterizedType) metaProperty.propertyGenericType();
    } catch (ClassCastException e) {
      return null;
    }

    while (true) {

      try {

        return (Class<?>) type.getActualTypeArguments()[0];

      } catch (ClassCastException cce) {

        type = (ParameterizedType) type.getActualTypeArguments()[0];

      }
    }

  }

  public static boolean isSupportedCollection(MetaProperty<?> metaProperty) {
    return List.class.isAssignableFrom(metaProperty.propertyType())
        || Set.class.isAssignableFrom(metaProperty.propertyType());
  }

  public static boolean isSupportedCollection(Property<Object> p) {
    return isSupportedCollection(p.metaProperty());
  }

  /**
   * given a property's {@link MetaProperty}, returns the class of the property. If the property's
   * class is {@link List} (or {@link Set}), returns the parameter type of the Collection. If the
   * property's class is {@link List} (or {@link Set}) and has no parameters, returns
   * <code>null</code>.
   */
  public static Class<?> genericClass(MetaProperty<?> metaProperty) {

    if (isSupportedCollection(metaProperty)) {

      ParameterizedType type = null;
      try {
        type = (ParameterizedType) metaProperty.propertyGenericType();
      } catch (ClassCastException e) {
        return null;
      }

      try {
        return (Class<?>) type.getActualTypeArguments()[0];
      } catch (ClassCastException cce) {

        // new: a List<Pointer<X>> is also allowed. (And returns Pointer.class)
        type = (ParameterizedType) type.getActualTypeArguments()[0];
        return (Class<?>) type.getRawType();

      }


    }

    return metaProperty.propertyType();
  }
  public static List<String> getEnumConstantsList(Property<Object> property) {

    return Arrays.asList(property.metaProperty().propertyType().getEnumConstants()).stream()
        .map(o -> o.toString()).collect(
            Collectors.toList());
  }

  /**
   * gets an instance of an enum given the enum value and the enum class.
   *
   * @param value the enum value. Not <code>null</code>.
   * @return an Enum or <code>null</code> if the enum value was not found in the enum class
   */
  public static <T extends Enum<T>> T getEnumInstance(final String value,
                                                      final Class<T> enumClass) {
    Objects.requireNonNull(value);

    try {
      return Enum.valueOf(enumClass, value);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

}
