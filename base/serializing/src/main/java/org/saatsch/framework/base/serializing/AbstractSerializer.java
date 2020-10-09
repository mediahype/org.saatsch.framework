package org.saatsch.framework.base.serializing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.io.AbstractDriver;

public abstract class AbstractSerializer {

  private static final Logger LOG = LoggerFactory.getLogger(AbstractSerializer.class);

  protected final XStream xstream;

  AbstractSerializer(AbstractDriver driver) {
    xstream = new XStream(driver);
  }

  /**
   * load on object of the given type from the given file. File is created if it does not exist.
   * If file is created, the given type must have the default no arg constructor.
   * 
   * @param type
   * @param file
   * @return
   * @throws SerializerException
   */
  public <T> T load(Class<T> type, File file) throws SerializerException {
    if (!file.exists()) {
      T newInstance;
      try {
        newInstance = type.newInstance();
      } catch (InstantiationException | IllegalAccessException e) {
        throw new SerializerException(e);
      }

      if (!save(file, newInstance)) {
        throw new SerializerException("File could not be created: " + file);
      }
    }

    FileInputStream fis = null;

    try {
      fis = new FileInputStream(file);
      Object ret = xstream.fromXML(fis);
      return (T) ret;
    } catch (IOException | ClassCastException e) {
      throw new SerializerException(e);
    }
  }

  public <T> T load(Class<T> type, InputStream is) throws SerializerException {
    try {
      Object ret = xstream.fromXML(is);
      return (T) ret;
    } catch (ClassCastException e) {
      throw new SerializerException(e);
    }
  }

  /**
   * saves an object into a file.
   * 
   * @param file
   * @param obj
   * @return <code>true</code>
   */
  public boolean save(File file, Object obj) {
    if (file.exists() && file.isDirectory()) {
      LOG.error("Error while saving: the given file is a directory.");
      return false;
    }

    FileOutputStream fos = null;
    OutputStreamWriter osw = null;

    try {
      fos = new FileOutputStream(file);
      osw = new OutputStreamWriter(fos, "UTF-8");
      xstream.toXML(obj, osw);
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      LOG.error("Error while saving: {}", e.getMessage());
      return false;
    } finally {
      closeQuietly(osw);
      closeQuietly(fos);
    }

    return true;

  }

  private static void closeQuietly(OutputStream output) {
    try {
      if (output != null) {
        output.close();
      }
    } catch (IOException ioe) {
      // ignore
    }
  }

  private static void closeQuietly(OutputStreamWriter output) {
    try {
      if (output != null) {
        output.close();
      }
    } catch (IOException ioe) {
      // ignore
    }
  }

  /**
   * serialize an object to a String
   * 
   * @param o
   * @return
   */
  public String objectToString(Object o) {
    return xstream.toXML(o);
  }

  /**
   * Prevents a field from being serialized. To omit a field you must always provide the declaring
   * type and not necessarily the type that is converted.
   * 
   * @param definedIn
   * @param fieldName
   * @return this
   */
  public AbstractSerializer omitField(Class<?> definedIn, String fieldName) {
    xstream.omitField(definedIn, fieldName);
    return this;
  }
  
  public AbstractSerializer registerConverter(Converter converter) {
    xstream.registerConverter(converter);
    return this;
  }

  public AbstractSerializer registerConverter(SingleValueConverter converter) {
    xstream.registerConverter(converter);
    return this;
  }
  
}
