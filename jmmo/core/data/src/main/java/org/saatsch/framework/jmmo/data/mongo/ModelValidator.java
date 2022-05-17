package org.saatsch.framework.jmmo.data.mongo;

import dev.morphia.annotations.Entity;
import org.joda.beans.Bean;
import org.joda.beans.MetaBean;
import org.joda.beans.MetaProperty;
import org.saatsch.framework.jmmo.data.annotations.Category;
import org.saatsch.framework.jmmo.data.annotations.CategoryType;
import org.saatsch.framework.jmmo.data.annotations.JmmoAppId;
import org.saatsch.framework.jmmo.data.annotations.JmmoCategories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Validates an object model.
 * 
 * @author saatsch
 *
 */
class ModelValidator {

  private static final Logger LOG = LoggerFactory.getLogger(ModelValidator.class);

  private static final String LOG_PREFIX = "While Validating: ";

  /**
   * this field is given by the constructor of this class. It maps the fully qualified class names
   * of the model baseClasses to the fully qualified class names of specialization classes.
   */
  private final Map<String, List<String>> classMappings;

  /**
   * Map of classes that appear in {@link #classMappings}. Keys are the Base Classes, values are the
   * Lists of their respective specializations.
   */
  private final Map<Class<?>, List<Class<?>>> classes = new LinkedHashMap<>();


  /**
   * Map of instances
   */
  private final Map<Object, List<Object>> instances = new LinkedHashMap<>();

  /**
   * <code>true</code> if {@link #validate()} was called and succeeded.
   */
  private boolean validated = false;


  /**
   * Consructor. After using this constructor, immediately call {@link #validate()}
   * 
   * @param classMappings maps base classes to specializations. This represents the model.
   */
  public ModelValidator(Map<String, List<String>> classMappings) {
    this.classMappings = classMappings;
  }

  /**
   * validation of the model. This method must be executed for this class to properly work. If the
   * validation completes successfully, it is known that:
   * <ul>
   * <li>All model classes can be found on the classpath</li>
   * <li>All model classes have a no arg constructor and can be instantiated. (which also means they
   * are not abstract)</li>
   * <li>All specialized classes inherit (directly or indirectly) from the respective base classes.
   * </li>
   * <li>All base classes inherit (directly or indirectly) from {@link EditorObject} (which implies
   * that all classes implement {@link Bean})</li>
   * <li>All Base Classes carry the {@link Entity} annotation.</li>
   * <li>All Base Classes have (or inherit from a class that has) a String property that is
   * annotated with {@link JmmoAppId}</li>
   * </ul>
   * 
   * 
   * 
   * @return <code>true</code> if the validation completed successfully.
   */
  public boolean validate() {


    if (!findClasses()) {
      logError("Not all model Classes could be loaded. Cannot continue");
      return false;
    }
    logInfo("model classes loaded OK ...");



    if (!instantiate()) {
      logError("Not all model Classes could be instantiated. Cannot continue");
      return false;
    }
    logInfo("model classes instantiated OK ...");


    if (!checkInheritance()) {
      logError("There are Errors in the inheritance in the Model. Cannot continue");
      return false;
    }

    if (!checkEntity()) {

      logError("All Base classes must carry the Entity annotation");
      return false;

    }

    if (!checkAppId()) {
      logError("All Base classes must have a String property that is annotated with @JmmoAppId");
      return false;

    }

    if (!checkCategories()) {
      logError("A check of the JmmoCategories annotation failed.");
      return false;
    }

    // TODO: check no @Reference
    // TODO: check BaseClass (or superclass thereof) exactly one AppId
    // TODO: check BaseClass (or superclass thereof) must have GivingName property. (Warning?)
    // TODO: check constraints given in the JmmoGivingName annotation.
    // TODO: appId property must not be internationalizable
    // TODO: (Warning) JmmoString is applied to a non string.
    // TODO: can a list of strings be a list of intlStrings ?
    // TODO: pointers must point to a base class
    // TODO: for everything that is a Bean, a metabean must be present.

    validated = true;
    return true;
  }



  private boolean checkCategories() {

    for (Class<?> c : classes.keySet()) {
      JmmoCategories categories = c.getAnnotation(JmmoCategories.class);
      if (null == categories) {
        // skip
        continue;
      }

      // categories annotation is present
      for (Category cat : categories.value()) {
        if (cat.type() == CategoryType.CLASS) {
          continue;
        }

        // category is property -> check if property exists
        MetaBean metaBean = MetaBean.of(c);
        if (!metaBean.metaPropertyExists(cat.propertyName())) {
          logError("Model class " + c + " does not contain a property named " + cat.propertyName()
              + " but needs it because of the " + JmmoCategories.class.getSimpleName()
              + " annotation");
          return false;
        }

        // and is of valid type.
        MetaProperty<Object> property = metaBean.metaProperty(cat.propertyName());
        if (!property.propertyType().isEnum() && !property.propertyType().equals(String.class)) {
          logError("property " + cat.propertyName() + " categorizes " + c
              + " and as such must be either a String or an Enum");
          return false;
        }

      }

    }

    return true;
  }

  /**
   * @return <code>true</code> if all Base Classes define exactly one property that is annotated
   *         with {@link JmmoAppId}.
   */
  private boolean checkAppId() {

    for (Class<?> c : classes.keySet()) {
      MetaBean metaBean = MetaBean.of(c);
      if (!hasAppIdProperty(metaBean)) {
        logError("Bean: " + metaBean.beanName()
            + " does not have a JmmoAppId property or that property is not a String");
        return false;
      }
    }
    return true;

  }


  private boolean hasAppIdProperty(MetaBean metaBean) {

    for (MetaProperty<?> metaProperty : metaBean.metaPropertyMap().values()) {

      try {
        metaProperty.annotation(JmmoAppId.class);
        if (metaProperty.propertyType().equals(String.class)) {
          return true;
        }
      } catch (NoSuchElementException ignored) {
      }
    }
    return false;
  }

  /**
   * @return <code>true</code> if all Base Classes carry the {@link Entity} annotation.
   */
  private boolean checkEntity() {

    boolean errorOccured = false;
    for (Class<?> c : classes.keySet()) {
      if (null == c.getAnnotation(Entity.class)) {
        logError(c.getName() + " does not carry the Entity annotation");
        errorOccured = true;
      }
    }

    return !errorOccured;
  }

  /**
   * checks inheritance.<br>
   * (1) All specialization classes must inherit from their respective base class.<br>
   * (2) All base classes must inherit from {@link MongoDataObject}<br>
   * When those checks are passed, it is also implicitly known that all classes mentioned in {@link #classMappings} are {@link MongoDataObject}s.<br>
   * 
   * @return <code>true</code> if the check passed, otherwise <code>false</code>.
   * 
   */
  private boolean checkInheritance() {

    boolean errorOccured = false;

    // check (1)
    for (Object base : instances.keySet()) {
      for (Object special : instances.get(base)) {
        if (!base.getClass().isAssignableFrom(special.getClass())) {
          logError(
              special.getClass().getName() + " must be a subclass of " + base.getClass().getName());
          errorOccured = true;
        }
      }
    }

    // check (2)
    for (Object base : instances.keySet()) {
      if (!(base instanceof MongoDataObject)) {
        LOG.error(LOG_PREFIX + "{} must be a subclass of {}", base.getClass().getName(),
            MongoDataObject.class.getName());
        errorOccured = true;
      }
    }

    return !errorOccured;

  }

  /**
   * tries to create one instance of each of the classes that are mentioned in the model.
   * 
   * @return true if all went well.
   */
  private boolean instantiate() {
    boolean errorOccured = false;
    // first pass. Look for constructors.
    for (Class<?> baseClass : classes.keySet()) {
      if (!hasNoArgConstructor(baseClass)) {
        errorOccured = true;
      }
      for (Class<?> specializedClass : classes.get(baseClass)) {
        if (!hasNoArgConstructor(specializedClass)) {
          errorOccured = true;
        }
      }
    }

    if (errorOccured) {
      return false;
    }

    // second pass. instantiate.

    for (Class<?> baseClass : classes.keySet()) {
      Object baseClassInstance = newInstance(baseClass);
      if (null == baseClassInstance) {
        return false;
      }

      List<Object> specializedClassInstances = new ArrayList<>();
      for (Class<?> specializedClass : classes.get(baseClass)) {
        Object newInstance = newInstance(specializedClass);
        if (null == newInstance) {
          return false;
        }
        specializedClassInstances.add(newInstance);
      }
      instances.put(baseClassInstance, specializedClassInstances);
    }


    return true;

  }



  /**
   * create a new instance of a class, using the no arg constructor.
   *
   * @param baseClass the class
   * @return the instantiated object or null if it could not be instantiated.
   */
  private Object newInstance(Class<?> baseClass) {

    Object o = null;

    try {
      o = baseClass.getConstructor().newInstance();
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException
        | SecurityException e) {
      LOG.error("Error instantiating " + baseClass.getName(), e);
    } catch (InstantiationException e) {
      // TODO: this is strictly speaking not a characteristic that prevents the Editor from working.
      LOG.error("Error instantiating {} : Class must not be abstract. ", baseClass.getName());
    }

    return o;

  }

  /**
   * tries to load the classes given in classMappings. If all goes well, {@link #classes} will be
   * filled after invocation of this method.
   * 
   * @return <code>true</code> if successful, <code>false</code> if one or more classes could not be
   *         loaded.
   */
  private boolean findClasses() {
    boolean errorOccured = false;
    for (String baseClass : classMappings.keySet()) {
      try {
        Class.forName(baseClass.trim());
      } catch (ClassNotFoundException e) {
        LOG.error(LOG_PREFIX + "Error Loading Class: {}", e.getMessage());
        errorOccured = true;
      }

      List<Class<?>> specializedClasses = new ArrayList<>();
      for (String specializedClass : classMappings.get(baseClass)) {
        try {
          specializedClasses.add(Class.forName(specializedClass.trim()));
        } catch (ClassNotFoundException e) {
          LOG.error(LOG_PREFIX + "Error Loading Class: {}", e.getMessage());
          errorOccured = true;
        }
      }

      try {
        classes.put(Class.forName(baseClass), specializedClasses);
      } catch (ClassNotFoundException e) {
        // ignore.
      }


    }
    return !errorOccured;
  }


  private static boolean hasNoArgConstructor(Class<?> clazz) {

    try {
      clazz.getConstructor();
      return true;
    } catch (NoSuchMethodException e) {
      LOG.error(LOG_PREFIX + "class {} does not have a no arg constructor", clazz);
    } catch (SecurityException e) {
      LOG.error(LOG_PREFIX + " security exception. ", e);
    }

    return false;

  }

  /**
   * Returns the object model's base classes.
   * 
   * @return a Set of classes. It contains those classes that are the object model's base classes.
   * @throws IllegalStateException if the model was not or unsuccessfully validated.
   */
  public Set<Class<?>> getBaseClasses() {
    assertValidated();
    return classes.keySet();
  }

  /**
   * gets the specializations for the given (object model's) base class.
   * 
   * @param c the object class
   * @return the specializations for the given object class or an empty list if there are none.
   * @throws IllegalStateException if the model was not or unsuccessfully validated.
   */
  public List<Class<?>> getSpecializationsFor(Class<?> c) {
    assertValidated();
    return classes.get(c);
  }

  /**
   * returns the base class of the given class or <code>null</code> if no base class could be found
   * in the known classes.
   * 
   * @param clazz the class
   * @return the base class of the given class or <code>null</code> if no base class could be found.
   */
  public Class<?> getBaseClassOf(Class<?> clazz) {
    for (Class<?> c : getBaseClasses()) {
      if (c.isAssignableFrom(clazz)) {
        return c;
      }
    }
    return null;
  }

  private void assertValidated() {
    if (!validated) {
      throw new IllegalStateException(
          "Model was not or unsuccessfully validated. Cannot continue.");
    }

  }

  public boolean isValidated() {
    return validated;
  }
  
  private void logError(String error) {
    LOG.error("{}{}", LOG_PREFIX, error);
  }

  private void logInfo(String info) {
    LOG.info("{}{}", LOG_PREFIX, info);
  }


}
