package org.saatsch.framework.base.serializing;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;


public class SerializingTest {

  @Rule
  public TemporaryFolder folder = new TemporaryFolder();
  
  private ObjectForSerializer obj = new ObjectForSerializer();
  
  @Test
  public void testJson() throws SerializerException, IOException {
    
    File createdFile =  folder.newFile("myJson.json"); 
    JsonSerializer serializer = SerializerFactory.newJsonSerializer();
    serializer.save(createdFile, obj);
    assertNotNull(serializer.objectToString(obj));
    
  }

  @Test
  public void testXml() throws SerializerException, IOException {
    
    File createdFile = folder.newFile("myXml.xml");
    XmlSerializer serializer = SerializerFactory.newXmlSerializer();
    serializer.save(createdFile, obj);
    serializer.load(ObjectForSerializer.class, createdFile);
    assertTrue(serializer.copyObject(obj).equals(obj));
    assertTrue(serializer.copyObject(obj, ObjectForSerializer.class) != obj);
    
    String objectToString = serializer.objectToString(obj);
    Object objFromXml = serializer.xmlToObject(objectToString);
    
    assertTrue(objFromXml.equals(obj));
    
  }

}
