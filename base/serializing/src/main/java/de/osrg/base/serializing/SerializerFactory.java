package de.osrg.base.serializing;

/**
 * Serializer Factory can create JSON and XML Serializers. JSON Serializer cannot deserialize.
 * XML Serializer has 2 operation modes: 
 * <ul>
 * <li>ID_REFERENCES (default mode) can handle cycles in object tree</li>
 * <li>NO_REFERENCES cannot handle cycles. Only usecase is for display.</li>
 * </ul>
 * 
 * @author saatsch
 *
 */
public class SerializerFactory {

  /**
   * @return a new instance of {@link XmlSerializer} with default operation mode ID_REFERENCES.
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
