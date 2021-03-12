package org.saatsch.framework.jmmo.data.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.joda.beans.Bean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaBean;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.saatsch.framework.jmmo.data.GivingNameComparator;
import org.saatsch.framework.jmmo.data.annotations.JmmoAppId;
import org.saatsch.framework.jmmo.data.annotations.JmmoDoc;
import org.saatsch.framework.jmmo.data.annotations.JmmoEditorHidden;
import org.saatsch.framework.jmmo.data.annotations.JmmoGivingName;
import org.saatsch.framework.jmmo.data.annotations.JmmoString;
import org.saatsch.framework.jmmo.data.annotations.StringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.base.util.Assert;

public class PropertyUtil {

  private static final Logger LOG = LoggerFactory.getLogger(PropertyUtil.class);

  private PropertyUtil() {
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


  /**
   * returns the first type argument of a property or <code>null</code> if the given property is not
   * a parameterized type or an unknown type (because it's defined generically).
   *
   * @param metaProperty the (description of the) property.
   */
  public static Class<?> firstTypeArg(MetaProperty<?> metaProperty) {

    ParameterizedType type;
    try {
      type = (ParameterizedType) metaProperty.propertyGenericType();
    } catch (ClassCastException e) {
      return null;
    }

    try {
      return (Class<?>) type.getActualTypeArguments()[0];
    } catch (ClassCastException e) {

      if (type.getActualTypeArguments()[0] instanceof ParameterizedType) {
        type = (ParameterizedType) type.getActualTypeArguments()[0];
        return (Class<?>) type.getRawType();
      }

      return null;

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
   * if the given object is a {@link Bean}, tries to find the full display name of the bean. If it
   * is a {@link Pointer}, returns {@link Pointer#asString()} Else returns o.toString();
   *
   * @see #getFullName(Bean)
   */
  public static String getNameOrToString(Object o) {

    if (null == o) {
      return "null";
    }

    if (!(o instanceof Bean)) {
      return o.toString();
    }

    String name = getFullName((Bean) o);

    if (null == name) {
      if (o instanceof Pointer) {
        return ((Pointer) o).asString();
      }
      return o.toString();
    }

    return name;

  }


  /**
   * gets an instance of an enum given the enum value and the enum class.
   *
   * @param value the enum value. Not <code>null</code>.
   * @return an Enum or <code>null</code> if the enum value was not found in the enum class
   */
  public static <T extends Enum<T>> T getEnumInstance(final String value,
      final Class<T> enumClass) {

    Assert.notNull(value);

    try {
      return Enum.valueOf(enumClass, value);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  /**
   * returns the constants of an enum if the given property represents an enum.
   *
   * @param property the {@link Property}
   * @return the enum constants or <code>null</code> if the given property does not represent an
   * enum.
   */
  public static Object[] getEnumConstants(Property<Object> property) {
    return property.metaProperty().propertyType().getEnumConstants();
  }

  public static List<String> getEnumConstantsList(Property<Object> property) {

    return Arrays.asList(property.metaProperty().propertyType().getEnumConstants()).stream()
        .map(o -> o.toString()).collect(
            Collectors.toList());
  }


  /**
   * @return <code>true</code> if the given {@link Property} represents a {@link Pointer}.
   */
  public static boolean isPointer(Property<Object> p) {
    return p.metaProperty().propertyType().equals(Pointer.class);
  }

  /**
   * returns <code>true</code> if the given property represents a {@link Bean}, otherwise
   * <code>false</code>. It does not matter if the current content of the given Property is null.
   *
   * @param p the given property.
   * @return <code>true</code> if the given property represents a {@link Bean}, otherwise
   * <code>false</code>
   */
  public static boolean isBean(Property<Object> p) {
    return Bean.class.isAssignableFrom(p.metaProperty().propertyType());
  }


  /**
   * given a property that holds a {@link Pointer}, returns the type to which the Pointer points.
   * TODO: check if argument is legal.
   *
   * @param p the {@link Property} to examine.
   * @return the type to which the Pointer points.
   */
  public static Class<?> getPointerType(Property<Object> p) {
    ParameterizedType type = (ParameterizedType) p.metaProperty().propertyGenericType();
    return (Class<?>) type.getActualTypeArguments()[0];
  }

  public static boolean isIntlString(Property<Object> property) {
    return isPropertyAnnotatedWith(property, JmmoString.class)
        && property.metaProperty().annotation(JmmoString.class).intl();
  }

  public static boolean isLongString(Property<Object> property) {
    return isPropertyAnnotatedWith(property, JmmoString.class)
        && property.metaProperty().annotation(JmmoString.class).style() == StringStyle.LONG;
  }


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


  public static List<Property<Object>> getPropertiesAnnotatedWith(Bean bean, Class annotation) {
    List<Property<Object>> ret = new ArrayList<>();
    for (String prop : bean.propertyNames()) {
      if (isPropertyAnnotatedWith(bean.property(prop), annotation)) {
        ret.add(bean.property(prop));
      }

    }
    return ret;
  }

  /**
   * returns the property of the given bean that is annotated with {@link JmmoAppId}.
   *
   * @param bean the bean.
   * @return the property or <code>null</code> if no property is annotated with {@link JmmoAppId}.
   */
  public static Property<Object> getAppIdProperty(Bean bean) {
    List<Property<Object>> list = getPropertiesAnnotatedWith(bean, JmmoAppId.class);
    if (!list.isEmpty()) {
      return firstProperty(list);
    }
    return null;
  }

  private static Property<Object> firstProperty(List<Property<Object>> props) {
    return props.iterator().next();
  }

  /**
   * returns the property of the given {@link Bean} that is annotated with {@link JmmoGivingName}
   * and {@link JmmoGivingName#main()} is set to <code>true</code>.
   *
   * @return the property or <code>null</code> if no matching property was found.
   */
  public static Property<Object> getGivingNameMainProperty(Bean bean) {
    List<Property<Object>> list = getPropertiesAnnotatedWith(bean, JmmoGivingName.class);
    for (Property<Object> prop : list) {
      JmmoGivingName annotation = prop.metaProperty().annotation(JmmoGivingName.class);
      if (annotation.main()) {
        return prop;
      }
    }
    return null;
  }

  public static List<Property<Object>> getStringProperties(Bean bean) {

    List<Property<Object>> ret = new ArrayList<>();

    for (MetaProperty<?> mp : MetaBean.of(bean.getClass()).metaPropertyIterable()) {
      if (mp.propertyType() == String.class) {
        ret.add((Property<Object>) bean.property(mp.name()));

      }
    }
    return ret;
  }

  /**
   * Gets the full display name of a Bean. Bean Properties annotated with {@link JmmoGivingName} are
   * taken into account and concatenated - separated by a space - in the order of their {@link
   * JmmoGivingName#cardinality()}. This method also respects Intl. Strings.
   *
   * @return the full name of a Bean or <code>null</code> if there was no {@link JmmoGivingName}
   * property.
   */
  public static String getFullName(Bean bean) {

    List<Property<Object>> givingNameProps = getPropertiesAnnotatedWith(bean, JmmoGivingName.class);

    if (givingNameProps.isEmpty()) {
      return null;
    }

    // shortcut if there is only one property that is givingName
    if (givingNameProps.size() == 1) {
      return intlService().loadLocalizedText(bean, firstProperty(givingNameProps));
    }

    Collections.sort(givingNameProps, new GivingNameComparator());

    StringBuilder sb = new StringBuilder();
    for (Property<Object> prop : givingNameProps) {
      String s = intlService().loadLocalizedText(bean, prop);
      if (!StringUtils.isEmpty(s)) {
        sb.append(s).append(" ");
      }
    }

    sb.setLength(sb.length() - 1);

    return sb.toString();

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


  private static IntlStringService intlService() {
    return JmmoContext.getBean(IntlStringService.class);
  }


  /**
   * returns the appId of the given Bean or <code>null</code> if the Bean does not define such a
   * property or the property is <code>null</code>
   *
   * @return the appId of the given Bean or <code>null</code> if the Bean does not define such a
   * property or the property itself is <code>null</code>
   */
  public static String getAppId(Bean bean) {

    Property<Object> appIdProperty = getAppIdProperty(bean);

    if (null == appIdProperty) {
      return null;
    }

    if (null == appIdProperty.get()) {
      return null;
    }

    return appIdProperty.get().toString();

  }

  public static MetaProperty<?> getPropertyAnnotatedWith(Class<?> beanClass, Class annotation) {
    MetaBean metaBean = MetaBean.of(beanClass);
    for (MetaProperty<?> metaProperty : metaBean.metaPropertyMap().values()) {
      try {
        metaProperty.annotation(annotation);
        return metaProperty;
      } catch (NoSuchElementException e) {
      }
    }
    return null;
  }


  public static MetaProperty<?> getAppIdProperty(Class<?> beanClass) {
    return getPropertyAnnotatedWith(beanClass, JmmoAppId.class);
  }


  /**
   * @return the name of the property annotated with {@link JmmoAppId} or <code>null</code> if there
   * is no such property.
   */
  public static String getAppIdPropertyName(Class<? extends Bean> beanClass) {
    MetaProperty<?> appIdMetaProperty = getAppIdProperty(beanClass);
    if (null == appIdMetaProperty) {
      return null;
    }
    return appIdMetaProperty.name();
  }


  /**
   * iterates over the properties of the given Bean. If a property is a pointer, initializes it.
   * This does not require DB interaction. TODO: is this a duplicate of the newly introduced visitor
   * pattern?
   */
  public static void initPointers(Bean bean) {

    for (String propName : bean.propertyNames()) {

      Property<Object> prop = bean.property(propName);

      // if it is a pointer, initialize
      if (Pointer.class.isAssignableFrom(prop.metaProperty().propertyType())) {
        Pointer<?> pointer = (Pointer<?>) prop.get();
        Class<?> extractTypeClass =
            JodaBeanUtils.extractTypeClass(prop.metaProperty(), prop.bean().getClass(), 1, 0);
        Property<Object> appIdProperty = getAppIdProperty(bean);
        String targetAppId = null;
        if (null != appIdProperty) {
          targetAppId = (String) appIdProperty.get();
        }
        pointer.setTargetCoodinate(extractTypeClass, targetAppId);
      }

      // if it is a bean, recurse
      if (Bean.class.isAssignableFrom(prop.metaProperty().propertyType())
          && null != (Bean) prop.get()) {
        initPointers((Bean) prop.get());
      }

    }
  }

  public static String getStringPropertyCoordinate(Bean bean, Property<Object> property) {
    return bean.metaBean().beanType().getSimpleName() + "/" + getAppId(bean) + "/"
        + property.name();
  }

  public static String getStringPropertyCoordinate(Bean bean, Property<Object> property,
      String appId) {
    return bean.metaBean().beanType().getSimpleName() + "/" + appId + "/" + property.name();
  }

  public static boolean isCollectionOfPointers(Property<Object> property) {
    return isSupportedCollection(property)
        && Pointer.class.equals(firstTypeArg(property.metaProperty()));
  }

  public static boolean isCollectionOfBeans(Property<Object> property) {
    return isSupportedCollection(property)
        && Bean.class.isAssignableFrom(firstTypeArg(property.metaProperty()));
  }

  /**
   * returns the properties of a bean except those that are annotated with {@link
   * JmmoEditorHidden}.
   *
   * @param bean the bean to inspect.
   * @return the list of visible properties. Not <code>null</code>
   */
  public static List<Property> visiblePropertiesOf(Bean bean) {
    List<Property> ret = new ArrayList<>();
    for (String name : bean.propertyNames()) {
      if (!isPropertyAnnotatedWith(bean.property(name), JmmoEditorHidden.class)) {
        ret.add(bean.property(name));
      }
    }
    return ret;
  }

  /**
   * returns the jmmodoc of a property or <code>null</code> if no jmmodoc is present on the
   * property.
   */
  public static String getJmmoDoc(Property<Object> property) {
    if (!isPropertyAnnotatedWith(property, JmmoDoc.class)) {
      return null;
    }

    return property.metaProperty().annotation(JmmoDoc.class).value();

  }

  /**
   * a simple copy bean method. Iterates over properties and calls getters and setters. Skips
   * properties that do not exist in the target bean.
   *
   * @param sourceBean the source Bean
   * @param targetBeanClass the class of the desired targetBean
   * @param resolveIntl if internationalizable strings should be resolved.
   * @return the new instance or <code>null</code> if there was no no-arg constructor.
   */
  public static <T extends Bean> T copyBean(Bean sourceBean, Class<T> targetBeanClass,
      boolean resolveIntl) {

    T targetBean = null;
    try {
      targetBean = targetBeanClass.getDeclaredConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException | NoSuchMethodException | SecurityException e) {
      LOG.error("Error: ", e);
      return null;
    }

    for (String prop : sourceBean.propertyNames()) {
      if (targetBean.propertyNames().contains(prop)) {
        copyProperty(sourceBean.property(prop), targetBean.property(prop), resolveIntl);
      }
    }
    return targetBean;
  }

  private static void copyProperty(Property<Object> source, Property<Object> target,
      boolean resolveIntl) {

    if (!resolveIntl) {
      target.set(source.get());
      return;
    }

    if (isIntlString(source)) {
      target.set(intlService().loadLocalizedText((String) source.get()));
      return;
    }

    target.set(source.get());

  }


}
