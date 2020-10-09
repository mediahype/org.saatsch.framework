package org.saatsch.framework.jmmo.data.config;

import java.io.File;
import java.io.InputStream;

import org.saatsch.framework.jmmo.data.api.DataConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.saatsch.framework.base.serializing.SerializerException;
import org.saatsch.framework.base.serializing.SerializerFactory;
import org.saatsch.framework.base.serializing.XmlSerializer;

/**
 * utility class that encapsulates how the data model configuration is saved/loaded.
 * 
 * @author saatsch
 *
 */
public class DataConfigurator {
  
  

  private static final Logger LOG = LoggerFactory.getLogger(DataConfigurator.class);

  private static final File CONFIG_FILE = new File(DataConfig.CONFIG_FILE_NAME);
  
  private static final XmlSerializer SERIALIZER = SerializerFactory.newXmlSerializer().referencesNone();

  private DataConfigurator() {}


  /**
   * load the (vendor-supplied) data config. This does not contain anything the end user can change.
   * 
   * @return the loaded {@link DataConfigModel}.
   * @throws FatalDataConfigException if config file could not be loaded.
   */
  public static synchronized DataConfigModel loadConfig() {
    
    if (CONFIG_FILE.exists()) {
      try {
        LOG.info("Using data model config file at: {}", CONFIG_FILE.getAbsolutePath());
        return SERIALIZER.load(DataConfigModel.class, CONFIG_FILE);
      } catch (SerializerException e) {
        
      }
      
    } else {
      InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(DataConfig.CONFIG_FILE_NAME);
      try {
        return SERIALIZER.load(DataConfigModel.class, is);
      } catch (SerializerException e) {
        throw new FatalDataConfigException("Could not load config file.", e);
      }
      
    }
    return null;
    
  }

  /**
   * saves a {@link DataConfigModel} to a file in the directory from which the JVM was started.
   * 
   * @param cfg the {@link DataConfigModel} to save.
   */
  public static synchronized void saveConfig(DataConfigModel cfg) {
    SERIALIZER.save(CONFIG_FILE, cfg);
  }


  
}
