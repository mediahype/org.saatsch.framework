package de.osrg.base.serializing;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * The XmlSerializer has 2 operation modes.
 * <ul>
 * <li>referencesId: This mode can handle cycles in the object graph and duplicate objects in the
 * graph will not be output multiple times. Lower readability.</li>
 * <li>referencesNone: This mode cannot handle cycles. It will fail if a cycle is present. Better
 * readability. Used e.g. with config files.</li>
 * </ul>
 * Default mode is referencesNone.
 * 
 * 
 * @author saatsch
 *
 */
public class XmlSerializer extends AbstractSerializer {


  public XmlSerializer() {
    super(new DomDriver());
    xstream.setMode(XStream.ID_REFERENCES);
  }

  /**
   * sets operation mode to NO_REFERENCES
   * 
   * @return this
   */
  public XmlSerializer referencesNone() {
    xstream.setMode(XStream.NO_REFERENCES);
    return this;
  }

  /**
   * sets operation mode.
   * 
   * @return this
   */
  public XmlSerializer referencesId() {
    xstream.setMode(XStream.ID_REFERENCES);
    return this;
  }

  /**
   * (deep) copy an object. Operation mode rules apply. See {@link XmlSerializer}.
   * 
   * @param o the object to copy.
   * @param clazz the class of the object (for type safety).
   * @return the copied object.
   */
  public <T> T copyObject(Object o, Class<T> clazz) {
    return (T) xstream.fromXML(xstream.toXML(o));
  }

  /**
   * (deep) copy an object. Operation mode rules apply. See {@link XmlSerializer}.
   * 
   * @param o the object to copy.
   * @return the copied object.
   */
  public Object copyObject(Object o) {
    return xstream.fromXML(xstream.toXML(o));
  }

  /**
   * deserialize xml to an object.
   * 
   * @param xmlInput
   * @return
   */
  public Object xmlToObject(String xmlInput) {
    return xstream.fromXML(xmlInput);
  }

}
