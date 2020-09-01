package de.jmmo.data.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This executable utility creates an example data configuration file.
 * 
 * @author saatsch
 *
 */
public class CreateDataConfigUtil {

  public static void main(String[] args) {

    // create data
    DataConfigModel cfg = new DataConfigModel();
    cfg.setAppName("my App Name");
    Map<String, List<String>> classMappings = cfg.getClassMappings();

    List<String> subclasses = new ArrayList<>();
    subclasses.add("fully.qualified.SpecializationOfBaseClass");

    classMappings.put("fully.qualified.BaseClass", subclasses);

    // save data
    DataConfigurator.saveConfig(cfg);


  }


}
