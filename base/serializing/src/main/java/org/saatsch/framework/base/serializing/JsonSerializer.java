package org.saatsch.framework.base.serializing;

import java.io.Writer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

/**
 * JsonSerializer can only serialize, not deserialize. Used for display or logging.
 * 
 * @author saatsch
 *
 */
public class JsonSerializer extends AbstractSerializer {

  // this driver does pretty printing
  private static final JsonHierarchicalStreamDriver driver1 = new JsonHierarchicalStreamDriver() {
    public HierarchicalStreamWriter createWriter(Writer writer) {
      return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
    }
  };

  // this driver prints in one line
  private static final  JettisonMappedXmlDriver driver2 = new JettisonMappedXmlDriver();

  public JsonSerializer() {
    super(driver1);
    xstream.setMode(XStream.NO_REFERENCES);
  }



}
