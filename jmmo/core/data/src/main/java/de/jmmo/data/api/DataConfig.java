package de.jmmo.data.api;

import de.jmmo.cdi.container.Lazy;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmmo.data.config.DataConfigModel;
import de.jmmo.data.config.DataConfigurator;
import de.jmmo.data.mongo.ModelManager;

/**
 * Grants access to the data configuration. It knows the ModelManager.
 * 
 * @author saatsch
 *
 */
public class DataConfig {
  private static final Logger LOG = LoggerFactory.getLogger(DataConfig.class);

  public static final String CONFIG_FILE_NAME = "jmmo-data-config.xml";

  private ModelManager mm;

  private String currentLanguage;

  private Lazy<DataConfigModel> config = Lazy.of(()-> DataConfigurator.loadConfig() );

  private DataConfigModel config(){
    return config.get();
  }

  public synchronized ModelManager getManager() {
    if (null == mm) {
      mm = new ModelManager(config().getClassMappings());
    }
    return mm;
  }

  /**
   * @return the name of the application as configured.
   */
  public String getAppName() {
    return config().getAppName();
  }
  
  /**
   * @return a Set of all base classes in the object model as configured in
   *         {@value #CONFIG_FILE_NAME}.
   */
  public Set<Class<?>> getBaseClasses() {
    return getManager().getBaseClasses();
  }


  /**
   * returns a List of all specialization classes for the given base class.
   * 
   * @param c the base class
   * @return a List of all specialization classes for the given base class.
   */
  public List<Class<?>> getSpecializationsFor(Class<?> c) {
    return getManager().getSpecializationsFor(c);
  }

  public String getCurrentLanguage() {
    if (null == currentLanguage) {
      currentLanguage = Locale.getDefault(Locale.Category.DISPLAY).getLanguage();
    }
    return currentLanguage;
  }

  public void setCurrentLanguage(String currentLocale) {
    this.currentLanguage = currentLocale;
  }

}
