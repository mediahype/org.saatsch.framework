package org.saatsch.framework.jmmo.cdi.container;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of a context for JMMO. It can be used to look up beans. only supports
 * ApplicationScoped beans. This context is suitable for usage on client and on server. It is also
 * independent of clustering. Which implies that there is one JmmoContext per JVM.
 *
 * It is additionally backed by a map that can be written to by calling {@link #putBean(Object)}.
 * This map is consulted when calling {@link #getBean(Class)}.
 *
 * @author saatsch
 */
public class JmmoContext {


  private static final Logger LOG = LoggerFactory.getLogger(JmmoContext.class);

  private JmmoContext() {}

  private static final Map<Class<?>, Object> beans = new ConcurrentHashMap<>();

  private static final Map<Class<?>, Class<?>> aliases = new ConcurrentHashMap<>();

  /**
   * create an alias.
   * 
   * @param from
   * @param to
   */
  public static void alias(Class<?> from, Class<?> to) {

    if (aliases.containsKey(to)) {
      throw new IllegalArgumentException("to class already exists as from alias.");
    }

    aliases.put(from, to);

  }

  /**
   * Lookup a bean. Respects aliases.
   *
   * @param type the type of bean to lookup.
   * @return the bean or <code>null</code> if it could not be found.
   * 
   * @see #alias(Class, Class)
   * 
   */
  public static <T> T getBean(Class<T> type) {

    // respect aliases. Find actual Type to get
    Class<?> typeToGet;
    if (aliases.containsKey(type)) {
      typeToGet = aliases.get(type);
    } else {
      typeToGet = type;
    }


    Object object = beans.get(typeToGet);
    if (object != null) {
      return (T) object;
    }

    Object newInstance = null;
    try {
      newInstance = typeToGet.getConstructor().newInstance();
    } catch (SecurityException | InstantiationException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
      throw new RuntimeException("Could not create instance of " + typeToGet.getName(), e);
    }

    beans.put(typeToGet, newInstance);


    return (T) newInstance;
  }

  /**
   * calls {@link #getBean(Class)} and returns an Optional of the result.
   */
  public static <T> Optional<T> getBeanOptional(Class<T> type) {
    return Optional.ofNullable(getBean(type));
  }

  /**
   *
   * @param bean the bean to put into the context.
   */
  public static void putBean(Object bean) {

    if (beans.get(bean) != null) {
      LOG.warn("Bean {} already present. Will be overwritten!", bean.getClass().getSimpleName());
    }
    
    beans.put(bean.getClass(), bean);
    

  }


}
