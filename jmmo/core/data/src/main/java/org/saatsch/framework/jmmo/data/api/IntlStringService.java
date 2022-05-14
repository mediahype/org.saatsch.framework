package org.saatsch.framework.jmmo.data.api;

import static org.saatsch.framework.jmmo.data.api.PropertyUtil.getStringPropertyCoordinate;

import java.util.List;

import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.api.beans.BeanService;
import org.saatsch.framework.jmmo.data.api.model.IntlString;
import org.saatsch.framework.jmmo.data.impl.BeanServiceImpl;
import org.saatsch.framework.jmmo.data.mongo.MorphiaMongoDataSink;
import dev.morphia.query.Query;

/**
 * data service for Localized Strings.
 * 
 * @author saatsch
 *
 */
public class IntlStringService {

  private static final Logger LOG = LoggerFactory.getLogger(IntlStringService.class);

  /**
  * the name of the coordinate property inside the {@link IntlString} class.
  */
  private static final String COORDINATE = IntlString.meta().coordinate().name();

  
  /**
   * the condition when filtering for the coordinate against the mongodb
   */
  private static final String COORDINATE_CONDITION = COORDINATE + " = ";

  private MorphiaMongoDataSink data = JmmoContext.getBean(MorphiaMongoDataSink.class);

  private DataConfig config = JmmoContext.getBean(DataConfig.class);

  private BeanService beanService = JmmoContext.getBean(BeanServiceImpl.class);



  /**
   * loads a localized string respecting the current locale given the localized string's coordinate.
   * If no localized String exists, it is created and an empty String is returned.
   * 
   * @param coordinate the coordinate of the IntlString
   * @return the content of the string
   */
  public String loadLocalizedText(String coordinate) {

    IntlString string = getOrCreateIntlString(coordinate);
    return selectIntlString(string);

  }


  /**
   * loads a localized string respecting the current locale. If no localized String exists, it is
   * created and an empty String is returned. If the given property is not a string, it throws an
   * {@link IllegalArgumentException}.
   * 
   * @param objectToEdit the Bean that contains - directly or indirectly - the String property. Not
   *        null.
   * @param property the property that represents the String. Not null.
   * 
   * @return the String
   * @throws IllegalArgumentException if the given property is not a string.
   */
  public String loadLocalizedText(Bean objectToEdit, Property<Object> property) {

    if (!(property.get() instanceof String)) {
      throw new IllegalArgumentException("given property must be a String");
    }

    // if the property is no intl. String
    if (!PropertyUtil.isIntlString(property)) {
      return (String) property.get();
    }


    String coordinate = (String) property.get();
    IntlString string = getOrCreateIntlString(coordinate);

    return selectIntlString(string);


  }

  /**
   * duplicates an Intl String
   * 
   * @param object the existing Object
   * @param property the property inside the existing object
   * @param newAppId the appId of the new Object
   * @return the new instance
   */
  public IntlString duplicate(Bean object, Property<Object> property, String newAppId) {
    String existingCoordinate = PropertyUtil.getStringPropertyCoordinate(object, property);
    IntlString existingString = getOrCreateIntlString(existingCoordinate);


    String newCoordinate = PropertyUtil.getStringPropertyCoordinate(object, property, newAppId);

    IntlString newString = new IntlString();
    newString.setCoordinate(newCoordinate);
    newString.setStrings(existingString.getStrings());
    data.save(newString);


    return newString;

  }

  private String selectIntlString(IntlString string) {
    return string.getForLanguage(config.getCurrentLanguage()); 
  }

  public synchronized void saveLocalizedText(String coordinate, String content) {
    saveLocalizedText(coordinate, content, config.getCurrentLanguage());
  }

  public synchronized void saveLocalizedText(String coordinate, String content, String language) {
    IntlString iString = getOrCreateIntlString(coordinate);
    iString.getStrings().put(language, content);
    data.save(iString);
  }
  

  /**
   * saves a localized text property to the database.
   * 
   * @param objectToEdit the object that is edited
   * @param property the text property
   * @param content the content of the text property
   */
  public synchronized void saveLocalizedText(Bean objectToEdit, Property<Object> property,
      String content) {
    String language = config.getCurrentLanguage();

    String coordinate = PropertyUtil.getStringPropertyCoordinate(objectToEdit, property);
    IntlString iString = getOrCreateIntlString(coordinate);

    iString.getStrings().put(language, content);

    data.save(iString);

  }


  public IntlString getIntlString(String coordinate) {
    return data.store().createQuery(IntlString.class).filter(COORDINATE_CONDITION, coordinate).get();
  }

  private IntlString getOrCreateIntlString(String coordinate) {

    IntlString ret =
        data.store().createQuery(IntlString.class).filter(COORDINATE_CONDITION, coordinate).get();

    if (null == ret) {
      IntlString newString = new IntlString();
      newString.setCoordinate(coordinate);
      data.save(newString);
      return newString;
    }

    return ret;
  }


  /**
   * returns all IntlStrings in the database.
   * 
   * @return all IntlStrings in the database.
   */
  public List<IntlString> loadAll() {
    return data.store().createQuery(IntlString.class).order(COORDINATE).asList();
  }

  /**
   * changes the coordinates of intl. Strings that are used inside the given Bean and writes those
   * changes to the database. This is part of the composite action that changes a Bean's appId, and
   * it is useless on it's own as it does not change the appId of the given Bean.
   * 
   * @param bean the Bean
   * @param newAppId the new appId
   */
  public void changeIntlStringCoordinate(Bean bean, String newAppId) {

    beanService.findIntlStrings(bean).forEach(result -> {

      IntlString intlString = data.store().createQuery(IntlString.class)
          .filter(COORDINATE_CONDITION, getStringPropertyCoordinate(bean, result.getProp())).get();

      // TODO: add exception if intlString is null

      intlString.setCoordinate(getStringPropertyCoordinate(bean, result.getProp(), newAppId));

      data.save(intlString);

    });
  }

  public void delete(Bean bean, Property<Object> prop) {
    Query<IntlString> query =
        data.store().createQuery(IntlString.class).filter(COORDINATE_CONDITION, prop.get());
    data.store().delete(query);
  }

  /**
   * TODO: also search in the content of {@link IntlString}s.
   *
   * @param term the search term
   * @return the result
   */
  public List<IntlString> search(String term){
    return data.store().createQuery(IntlString.class).field(COORDINATE).contains(term).asList();
  }

}
