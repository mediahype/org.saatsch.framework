package de.jmmo.data.mongo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.MetaBean;
import org.joda.beans.MetaProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmmo.data.api.Pointer;
import de.jmmo.data.api.PropertyUtil;

/**
 * 
 * this class is used after the model has been validated. It inspects the model and records any
 * links between classes of the model.
 * 
 * @author saatsch
 *
 */
class ModelInspector {

  private static final Logger LOG = LoggerFactory.getLogger(ModelInspector.class);

  private ModelManager manager;

  /**
   * contains property types that can be skipped during inspection.
   */
  private static List<Class<?>> skippable = new ArrayList<>();

  /**
   * this contains all references in the model. It is filled while the inspector inspects the model.
   */
  private static Set<ModelReference> refs = new HashSet<>();

  static {
    skippable.add(String.class);
    skippable.add(Integer.class);
    skippable.add(Float.class);
  }

  public ModelInspector(ModelManager modelManager) {
    this.manager = modelManager;
  }


  /**
   * entry point to model inspection.
   */
  public void inspect() {

    for (Class<?> c : manager.getBaseClasses()) {
      inspectObjectClass(c);
    }
    printRefs();


  }


  /**
   * outputs collected references to LOG info.
   */
  private void printRefs() {
    StringBuilder sb = new StringBuilder();
    for (ModelReference ref : refs) {
      sb.append(ref.toString()).append("\n");
    }
    LOG.info("Refs: \n{}", sb);
  }


  private void inspectObjectClass(Class<?> clazz) {
    for (Class<?> c : manager.getSpecializationsFor(clazz)) {
      inspectSpecialization(c);
    }
  }


  private void inspectSpecialization(Class<?> c) {


    LOG.info("inspecting {}", c.getName());
    String coordinateBuilder = c.getName();
    inspectMetaBean(MetaBean.of(c), coordinateBuilder);
    LOG.info("-----------");

  }

  /**
   * this method is connected to {@link #inspectPropertyType(MetaProperty, String)} and together
   * with it forms a recursion into properties that are {@link Bean}s. This is the entry point to
   * inspecting a {@link Bean} for references to other Beans. The purpose of this method is to
   * collect a list of inter-entity links. This list will be helpful in A) issuing a warning if an
   * entity that is referenced from another entity should be deleted - which would leave a dead
   * link. And in B) duplicating an entity.
   * 
   * @param metaBean
   * @param coordinateBuilder
   */
  private void inspectMetaBean(MetaBean metaBean, String coordinateBuilder) {

    metaBean.metaPropertyIterable().forEach(metaProp -> {
      if (!canSkipType(metaProp)) {
        inspectPropertyType(metaProp, coordinateBuilder);
      }
    });
  }

  /**
   * inspects a property for it's type. If the property is a {@link Bean}, calls
   * {@link #inspectMetaBean(MetaBean, String)}, thereby recursing
   * 
   * @param metaProp the property
   * @param coordinateBuilder
   */
  private void inspectPropertyType(MetaProperty<?> metaProp, String coordinateBuilder) {
    // A collection can
    if (PropertyUtil.isSupportedCollection(metaProp)) {
      Class<?> genericClass = PropertyUtil.genericClass(metaProp);

      // reference something unknown
      if (null == genericClass) {
        return;
      }
      
      // reference an entity
      if (manager.isManagedClass(genericClass)) {
        refs.add(new ModelReference(genericClass,
            new PropertyMongoCoordinate(coordinateBuilder + ":" + metaProp.name())));
      }

      // reference a Bean that is not an entity.
      if (Bean.class.isAssignableFrom(genericClass) && !manager.isManagedClass(genericClass)) {
        MetaBean metaBean = MetaBean.of(genericClass);
        // recurse
        inspectMetaBean(metaBean, coordinateBuilder + ":" + metaProp.name());
      }

      // be a collection of pointers
      // this covers e.g. List<Pointer<BaseClass>>
      if (Pointer.class.isAssignableFrom(genericClass)) {
        refs.add(new ModelReference(PropertyUtil.firstTypeArgRecurse(metaProp),
            new PropertyMongoCoordinate(coordinateBuilder + ":" + metaProp.name())));
      }

    }

    // A Pointer
    if (metaProp.propertyType().equals(Pointer.class)) {
      Class<?> firstTypeArg = PropertyUtil.firstTypeArg(metaProp);
      if (null != firstTypeArg) {
        refs.add(new ModelReference(firstTypeArg,
            new PropertyMongoCoordinate(coordinateBuilder + ":" + metaProp.name())));        
      }
    }


    // A Bean
    if (Bean.class.isAssignableFrom(metaProp.propertyType())) {
      MetaBean metaBean = MetaBean.of(metaProp.propertyType());
      // recurse
      inspectMetaBean(metaBean, coordinateBuilder + ":" + metaProp.name());
    }
  }



  /**
   * returns <code>true</code> if the given {@link MetaProperty} can be skipped during inspection.
   * This is determined by the given {@link MetaProperty}'s type and enums and the types given in
   * {@link #skippable} can be skipped.
   * 
   * @param metaProp
   * @return <code>true</code> if the given {@link MetaProperty} can be skipped during inspection.
   */
  private boolean canSkipType(MetaProperty<?> metaProp) {
    if (skippable.contains(metaProp.propertyType())) {
      return true;
    }

    if (metaProp.propertyType().isEnum()) {
      return true;
    }
    return false;

  }


  /**
   * returns the incoming references to the given clazz.
   * 
   * @param clazz should be a base class
   * @return
   */
  public Set<ModelReference> getIncomingReferences(Class<?> clazz) {

    Set<ModelReference> ret = new HashSet<>();

    for (ModelReference r : refs) {
      if (r.getToClass().equals(clazz)) {
        ret.add(r);
      }
    }
    return ret;
  }

  public Set<ModelReference> getOutgoingReferences(Class<?> clazz) {

    Set<ModelReference> ret = new HashSet<>();

    for (ModelReference r : refs) {
      if (clazz.equals(r.getFrom().getClazz())) {
        ret.add(r);
      }
    }

    return ret;

  }

  public Set<ModelReference> getAllReferences(Class<?> clazz) {

    Set<ModelReference> ret = new HashSet<>();

    ret.addAll(getIncomingReferences(manager.getBaseClassOf(clazz)));
    ret.addAll(getOutgoingReferences(clazz));

    return ret;

  }

}
