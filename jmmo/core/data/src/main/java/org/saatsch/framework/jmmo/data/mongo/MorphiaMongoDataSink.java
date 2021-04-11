package org.saatsch.framework.jmmo.data.mongo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.joda.beans.Bean;
import org.joda.beans.MetaProperty;
import org.saatsch.framework.jmmo.data.DataSink;
import org.saatsch.framework.jmmo.data.DupKeyException;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoDatabase;
import com.mongodb.gridfs.GridFS;

import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.mapping.MapperOptions;
import dev.morphia.query.Query;

/**
 * Jmmo's access to data. A user application must provide an implementation of this abstract class
 * where
 * <ul>
 * <li>it provides the packages that contain the user model</li>
 * <li>it provides the db server and db name that should be used.</li>
 * </ul>
 * 
 * 
 * @author saatsch
 * 
 */
public abstract class MorphiaMongoDataSink implements DataSink {

  private static final Logger LOG = LoggerFactory.getLogger(MorphiaMongoDataSink.class);

  protected MongoDatabase mongoDb;

  protected MongoClient mongoClient;

  private Morphia morphia;

  protected Datastore morphiaDatastore;

  private boolean initialized;
  
  private GridFS files;

  protected void init() {

    LOG.info("Morphia Mongo DataSink initializing ...");

    initMongo();
    initMorphia();


    LOG.info("Morphia Mongo DataSink initialized. Implementation is: {}", this.getClass());

    initialized = true;
  }

  private void initMorphia() {
    morphia = new Morphia();
    
    
    morphia.getMapper().setOptions(MapperOptions.builder().storeEmpties(false).build());
    morphia.mapPackage("org.saatsch.framework.jmmo.data.api.model");
    mapPackages(morphia);
    morphiaDatastore = morphia.createDatastore(mongoClient, dbName());
    morphiaDatastore.ensureIndexes();

  }

  /**
   * initializes mongo. As the server needs it's database, it should not start if mongo is not
   * present. This method executes a ping, which throws an unchecked exception if it does not
   * succeed. Because this method is called from inside the init() method, we expect the server to
   * not start if an unchecked exception is thrown from here.
   */
  private void initMongo() {

    mongoClient = new MongoClient(dbServer(),
        MongoClientOptions.builder().serverSelectionTimeout(1000).build());

    mongoDb = mongoClient.getDatabase(dbName());

    // if this fails, it fails after the serverSelectionTimeout !
    mongoDb.runCommand(new Document("ping", 1));
    
    files = new GridFS(mongoClient.getDB(dbName()));
  }

  @Override
  public void delete(Object o) {
    morphiaDatastore.delete(o);
  }

 
  

  @SuppressWarnings("unchecked")
  public <T> T save(Class<T> expected, Object obj) {
    return (T) save(obj);
  }


  @Override
  public Object save(Object obj) {

    try {
      morphiaDatastore.save(obj);
      return obj;
    } catch (DuplicateKeyException dke) {
      LOG.error("duplicate key while saving: {}", obj);
      throw new DupKeyException(dke);
    }
  }

  public Object save(Object obj, WriteConcern wc) {

    try {
      morphiaDatastore.save(obj, wc);
      return obj;
    } catch (DuplicateKeyException dke) {
      LOG.error("duplicate key while saving: {}", obj);
      throw new DupKeyException(dke);
    }
  }


  /**
   * 
   * @return the morphia {@link Datastore}. This is the interface to all custom operations on the
   *         mongo database that use mapped objects.
   */
  public Datastore store() {
    return morphiaDatastore;
  }

  @Override
  public <T> T reload(Class<T> expected, Object obj) {

    if (null == expected || null == obj) {
      return null;
    }

    ObjectId id = getId(obj);
    if (null == id) {
      return null;
    }

    // TODO: maybe we must catch a class cast exception ?
    Query<T> find = morphiaDatastore.find(expected, "_id", id);
    return find.get();
  }

 
  /**
   * extracts the id out of an Object, assuming the object has a "getId()" method and this method
   * returns an {@link ObjectId}.
   * 
   * @param obj
   * @return
   */
  protected ObjectId getId(Object obj) {
    ObjectId ret = null;
    Method method = null;
    try {
      method = obj.getClass().getMethod("getId");
    } catch (NoSuchMethodException | SecurityException e) {
    }

    if (null != method) {
      try {
        ret = (ObjectId) method.invoke(obj);
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
          | ClassCastException e) {
      }
    }

    return ret;
  }

  public List<?> newList() {
    return new ArrayList();
  }

  @Override
  public <T> List<T> getAll(Class<T> expected) {
    LOG.trace("get all: {}", expected.getName());
    return morphiaDatastore.find(expected).asList();
  }

  @Override
  public <T> List<T> getAllExact(Class<T> expected) {
    LOG.info("get all exact: {}", expected.getName());
    return morphiaDatastore.find(expected).disableValidation()
        .filter("className == ", expected.getName()).asList();
  }

  public <T> Query<T> getAllExactQuery(Class<T> expected) {
    return morphiaDatastore.find(expected).disableValidation().filter("className == ",
        expected.getName());
  }

  /**
   * the implementation must map the packages with classes that contain the user model. Call
   * {@link Morphia#mapPackage(String)} to map a package. Packages are not searched recursively.
   * Only packages explicitly given here are mapped. Classes in those packages are scanned for
   * Morphia annotations.
   * 
   * Object instances of classes in mapped packages can be stored in the database.
   * 
   */
  protected abstract void mapPackages(Morphia morphia);

  /**
   * @return the name of the database to use.
   */
  protected abstract String dbName();

  /**
   * @return the IP name of the db server to use.
   */
  protected abstract String dbServer();

  public boolean isInitialized() {
    return initialized;
  }


  public List<Bean> applyFilterCondition(ReferenceFilterCondition ref) {

    if (null == ref) {
      return new ArrayList<>();
    }

    return (List<Bean>) store().createQuery(ref.getClazz()).disableValidation()
        .filter(ref.getCondition(), ref.getValue()).filter("className = ", ref.getClazz().getName())
        .asList();
  }

  /**
   * TODO: use.
   * 
   * @param collectionClass
   * @return
   */
  public List getDistinctClasses(Class collectionClass) {
    return store().getCollection(collectionClass).distinct("className");
  }

 
  public <T> T getByAppId(Class<T> collectionClass, String appId) {
    
    MetaProperty<?> appIdProperty = PropertyUtil.getAppIdProperty(collectionClass);
    return store().createQuery(collectionClass).filter(appIdProperty.name() + " = ", appId).get();
    
  }
  
  public Document runNative(Document command) {
    return mongoDb.runCommand(command);
  }

  public GridFS getFiles() {
    return files;
  }
  
}
