package de.jmmo.data.api;

import org.joda.beans.Bean;

import de.jmmo.cdi.container.JmmoContext;
import de.jmmo.data.mongo.MorphiaMongoDataSink;

/**
 * 
 * 
 * @author saatsch
 *
 */

public class AppIdSuggester {



  private MorphiaMongoDataSink data = JmmoContext.getBean(MorphiaMongoDataSink.class);

  /**
   * @param desiredId the desired id
   * @param clazz the class of the object that requests the id.
   * @return the given desiredId if the desiredId is not already taken, otherwise an id that
   * 
   */
  public String suggest(String desiredId, Class<? extends Bean> clazz) {

    String suggested = normalize(desiredId);
    String appId = PropertyUtil.getAppIdPropertyName(clazz);


    while (true) {
      Bean object = data.store().createQuery(clazz).filter(appId + " = ", suggested).get();

      if (null == object) {
        return suggested;
      } else {
        suggested = incrementString(suggested);
      }
    }
  }

  public String normalize(String in) {
    // remove whitespaces.
    
    String ret = in;
    
    if (ret.contains(" ")) {
      ret = ret.replace(" ", "");
    }

    if (ret.contains("/")) {
      ret = ret.replace("/", "");
    }

    return ret;
    // TODO: remove e.g. umlauts
  }

  /**
   * increment a string means that a number is appended to a string. If the last characters of the
   * given string already form a number, that number is increased by 1.
   * 
   * @param str
   * @return
   */
  public static String incrementString(String str) {

    if (str == null || str.length() == 0) {
      return "1";
    }

    int i = 0;
    while (Character.isDigit(str.charAt(str.length() - i - 1))) {
      i++;
      int x = str.length();
      if (i >= x) {
        break;
      }
    }

    if (i == 0) {
      return str + "1";
    }

    String number = str.substring(str.length() - i);
    String begin = str.substring(0, str.length() - i);

    Long l = Long.parseLong(number) + 1;

    return begin + l.toString();

  }

}
