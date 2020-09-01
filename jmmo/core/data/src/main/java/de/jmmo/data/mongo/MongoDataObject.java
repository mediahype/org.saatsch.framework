package de.jmmo.data.mongo;

import org.bson.types.ObjectId;

import de.jmmo.data.DataObject;
import dev.morphia.annotations.Id;


public abstract class MongoDataObject implements DataObject<ObjectId>{

  private static final long serialVersionUID = 9143069409082170884L;

  /**
   * the database-managed unique id of the {@link MongoDataObject}.
   */
  @Id
  private ObjectId id;

  /**
   * @return the database-managed unique id of this {@link MongoDataObject}.
   */
  public ObjectId getId() {
    return id;
  }


}
