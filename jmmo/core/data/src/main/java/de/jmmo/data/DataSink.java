package de.jmmo.data;

import java.util.List;

/**
 * a simple data sink interface.
 * 
 * 
 * @author saatsch
 * 
 */
public interface DataSink {

  /**
   * saves an object to the persistent store. The object typically carries meta information on how
   * it should be saved.
   * 
   * @param obj the object to be saved.
   * @return the saved Object
   */
  Object save(Object obj) throws DupKeyException;


  /**
   * typesafe call to {@link #save(Object)} 
   * 
   * @param expected
   * @param obj
   * @return
   */
  public <T> T save(Class<T> expected, Object obj);

  /**
   * reloads an object that was previously loaded, but could have changed in the meantime. Note that
   * the given object does not change in the process and the returned object is not == the given.
   * 
   * @param expected the expected class
   * @param obj the previously loaded Object. Note that this does not get updated.
   * @return the current version of the Object or <code>null</code> if no such object was found.
   */
  <T> T reload(Class<T> expected, Object obj);

  /**
   * returns all objects of the given expected class and it's subclasses.
   * 
   * @param expected
   * @return
   */
  <T> List<T> getAll(Class<T> expected);

  /**
   * returns all objects of the exact given expected class and <em>NOT</em> it's subclasses.
   * 
   * @param expected
   * @return
   */
  <T> List<T> getAllExact(Class<T> expected);

  void delete(Object o);



}
