package de.jmmo.data.mongo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains the necessary data to locate/query for a property. Instances of this class are
 * immutable.
 * 
 * @author saatsch
 *
 */
class PropertyMongoCoordinate {

  private static final Logger LOG = LoggerFactory.getLogger(PropertyMongoCoordinate.class);

  private final String coordinateString;

  private final String className;

  /**
   * constructs a new {@link PropertyMongoCoordinate} from a String. 
   * 
   * @param coordinateString
   */
  public PropertyMongoCoordinate(String coordinateString) {
    this.coordinateString = coordinateString;
    className = coordinateString.split(":")[0];
  }

  /**
   * @return the class of this {@link PropertyMongoCoordinate} or <code>null</code> if that class
   *         could not be found.
   */
  public Class<?> getClazz() {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      LOG.warn("Class: {} not found", className);
      LOG.trace("", e);
      return null;
    }
  }

  /**
   * the coordinate String split at the delimiter.
   * 
   * @return the coordinate String split at the delimiter.
   */
  public String[] getProperties() {
    return coordinateString.substring(coordinateString.indexOf(':') + 1).replace(":", ".")
        .split("\\.");
  }


  @Override
  public String toString() {
    return coordinateString;
  }

}
