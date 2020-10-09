package org.saatsch.framework.jmmo.data.mongo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The model manager knows - and has validated and inspected - the object model. The object model is
 * a map that maps the names of base classes to lists of names of specialization classes.
 * 
 * @author saatsch
 *
 */
public class ModelManager {

  private ModelValidator validator;
  private ModelInspector inspector;


  public ModelManager(Map<String, List<String>> classMappings) {

    validator = new ModelValidator(classMappings);

    if (!getValidator().validate()) {
      throw new RuntimeException("JMMO Data Model not valid. See previous Error(s).");
    }

    inspector = new ModelInspector(this);
    inspector.inspect();



  }

  public Set<Class<?>> getBaseClasses() {
    return getValidator().getBaseClasses();
  }

  public List<Class<?>> getSpecializationsFor(Class<?> c) {
    return getValidator().getSpecializationsFor(c);
  }

  // TODO: make this return Class<? extends Bean>
  public Class<?> getBaseClassOf(Class<?> c) {
    return getValidator().getBaseClassOf(c);
  }

  public ModelValidator getValidator() {
    return validator;
  }


  /**
   * a class is managed, if it is or derives from a base class.
   * 
   * @param clazz
   * @return
   */
  public boolean isManagedClass(Class<?> clazz) {
    for (Class<?> c : getValidator().getBaseClasses()) {
      if (c.isAssignableFrom(clazz)) {
        return true;
      }
    }
    return false;
  }


  /**
   * returns the incoming references to the given model class. Useful to answer the question if an
   * Object can be safely deleted.
   * 
   * @param clazz the class to get the incoming references for. This must be a base class.
   * 
   * @return the incoming references to the given class. Never <code>null</code>
   */
  public Set<ModelReference> getIncomingModelReferences(Class<?> clazz) {
    return inspector.getIncomingReferences(clazz);
  }
  
  /**
   * returns all model references (incoming and outgoing) to and from the given clazz. 
   * 
   * @param clazz must be a managed class.
   * @return the known references to and from the given clazz. 
   */
  public Set<ModelReference> getAllModelReferences(Class<?> clazz) {
    return inspector.getAllReferences(clazz);
  }

}
