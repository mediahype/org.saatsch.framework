package de.jmmo.data.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The model for the vendor-supplied data config.
 * 
 * @author saatsch
 *
 */
public class DataConfigModel {

  /**
   * maps the fully qualified class names of the data model base classes to lists of the fully
   * qualified class names of the data model specialization classes.
   */
  private Map<String, List<String>> classMappings = new LinkedHashMap<>();
  
  /**
   * the client defined appName
   */
  private String appName = "";


  public Map<String, List<String>> getClassMappings() {
    return classMappings;
  }


  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }
  
  
  
 

}
