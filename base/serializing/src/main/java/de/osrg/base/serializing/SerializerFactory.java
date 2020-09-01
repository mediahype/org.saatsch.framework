package de.osrg.base.serializing;

public class SerializerFactory {

  /**
   * @return a new instance of {@link XmlSerializer}.
   */
  public static XmlSerializer newXmlSerializer() {
    return new XmlSerializer();
  }
  
  /**
   * @return a new instance of {@link JsonSerializer}.
   */
  public static JsonSerializer newJsonSerializer() {
    return new JsonSerializer();
  }
  
  
}
