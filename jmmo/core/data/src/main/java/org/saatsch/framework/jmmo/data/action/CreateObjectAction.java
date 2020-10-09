package org.saatsch.framework.jmmo.data.action;

import java.util.List;

import org.joda.beans.Bean;
import org.joda.beans.Property;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.api.AppIdSuggester;
import org.saatsch.framework.jmmo.data.api.DataConfig;
import org.saatsch.framework.jmmo.data.api.IntlStringService;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;
import org.saatsch.framework.jmmo.data.mongo.MorphiaMongoDataSink;

/**
 * upon executing this IAction it creates a new Object and saves it to the database. The newly
 * created Object is returned from the {@link #execute()} method.
 * 
 * @author saatsch
 *
 */
public class CreateObjectAction implements IAction {

  private static final IntlStringService stringService = JmmoContext.getBean(IntlStringService.class);
  private final String subclass;
  /**
   * the name of the object that is created.
   */
  private final String name;
  private final MorphiaMongoDataSink data = JmmoContext.getBean(MorphiaMongoDataSink.class);
  private final AppIdSuggester suggester = JmmoContext.getBean(AppIdSuggester.class);
  private final List<Class<?>> subclasses;
  private final String desiredId;
  private final Class<? extends Bean> objectClass;

  /**
   * this constructor is used in places where only the simpleName of the sublass is known.
   * 
   * @param name the name of the object
   * @param desiredId the desired appId of the object
   * @param subclass the simpleName of the subclass
   * @param objectBaseClass
   */
  public CreateObjectAction(String name, String desiredId, String subclass,
      Class<? extends Bean> objectBaseClass) {
    this.name = name;
    this.subclass = subclass;
    subclasses = JmmoContext.getBean(DataConfig.class).getSpecializationsFor(objectBaseClass);
    this.desiredId = desiredId;
    this.objectClass = null;
  }

  public CreateObjectAction(String name, String desiredId, Class<? extends Bean> objectClass) {
    this.name = name;
    this.subclass = null;
    subclasses = JmmoContext.getBean(DataConfig.class).getSpecializationsFor(objectClass);
    this.desiredId = desiredId;
    this.objectClass = objectClass;
  }

  public CreateObjectAction(String name, Class<? extends Bean> objectClass) {
    this.name = name;
    this.subclass = null;
    subclasses = JmmoContext.getBean(DataConfig.class).getSpecializationsFor(objectClass);
    this.desiredId = name;
    this.objectClass = objectClass;
  }



  public Object execute() {
    Bean newInstance;

    // create the new instance
    if (null != subclass) {
      newInstance = PropertyUtil.newInstance((Class<? extends Bean>) findInSubclasses(subclass));
    } else {
      newInstance = PropertyUtil.newInstance(objectClass);
    }

    Property<Object> namingProperty = PropertyUtil.getGivingNameMainProperty(newInstance);
    PropertyUtil.getAppIdProperty(newInstance).set(suggester.normalize(desiredId));
    PropertyUtil.initPointers(newInstance);

    if (PropertyUtil.isIntlString(namingProperty)) {
      namingProperty.set(PropertyUtil.getStringPropertyCoordinate(newInstance, namingProperty));
    } else {
      namingProperty.set(name);
    }

    // the first write operation against the database must be to try and save the object because when a
    // DupKeyException is raised, nothing else has happened.
    data.save(newInstance);

    // now we know that the appId of the newInstance is valid and can go on.
    for ( Property<Object> p : PropertyUtil.getStringProperties(newInstance)) {
      if (PropertyUtil.isIntlString(p)) {
        stringService.saveLocalizedText(newInstance, p, "");
        p.set(PropertyUtil.getStringPropertyCoordinate(newInstance, p));
      }
    }

    if (PropertyUtil.isIntlString(namingProperty)) {
      stringService.saveLocalizedText(newInstance, namingProperty,
          name);
    }

    
    data.save(newInstance);
    return newInstance;

  }



  private Class<?> findInSubclasses(String className) {
    for (Class<?> c : subclasses) {
      if (c.getSimpleName().equals(className)) {
        return c;
      }
    }
    return null;
  }

}
