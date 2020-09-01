package de.osrg.tools.apiclient;

import java.io.File;

import de.osrg.base.serializing.SerializerException;
import de.osrg.base.serializing.SerializerFactory;
import de.osrg.base.serializing.XmlSerializer;

/**
 * static utility to load and save user data for the ApiClient.
 * 
 * @author saatsch
 *
 */
public class ApiClientUserData {

  private static final File CONFIG_FILE =  new File("userData.xml");
  private static final XmlSerializer SERIALIZER = SerializerFactory.newXmlSerializer().referencesNone();
  
  private static UserDataModel data; 
  
  private ApiClientUserData() {}
  
  public static UserDataModel getUserData() {
    
    if (data == null) {
      try {
        data = SERIALIZER.load(UserDataModel.class,  CONFIG_FILE);
      } catch (SerializerException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }      
    }
    
    return data;
    
  }
  
  public static void saveUserData() {
    
    SERIALIZER.save(CONFIG_FILE, data);
    
  }
  
}
