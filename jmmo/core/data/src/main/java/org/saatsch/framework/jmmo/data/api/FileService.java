package org.saatsch.framework.jmmo.data.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.DataException;
import org.saatsch.framework.jmmo.data.DupKeyException;
import org.saatsch.framework.jmmo.data.api.model.JmmoFile;
import org.saatsch.framework.jmmo.data.DataSink;
import org.saatsch.framework.base.UserException;

/**
 * File service for use on a machine that has direct connection to the database (server).
 * 
 * @author saatsch
 *
 */
public class FileService {

  private DataSink data = JmmoContext.getBean(DataSink.class);


  /**
   * create a new file from a fully qualified filename on the file system
   * 
   * @param fileName
   * @return
   */
  public JmmoFile newFile(String fileName) {

    requireFileDoesNotExist(fileName);

    try {
      GridFSInputFile createFile = data.getFiles().createFile(new File(fileName));
      return _newFile(createFile, fileName);
    } catch (IOException e) {
      throw new UserException("Problems saving file: ", e);
    }
  }

  public JmmoFile newFile(InputStream file, String fileName) {

    requireFileDoesNotExist(fileName);

    GridFSInputFile createFile = data.getFiles().createFile(file);
    return _newFile(createFile, fileName);
  }

  private JmmoFile _newFile(GridFSInputFile file, String fileName) {
    file.setFilename(fileName);
    file.save();
    JmmoFile img = new JmmoFile();
    img.setFilename(fileName);
    data.save(img);
    return img;

  }

  /**
   * gets a file from the mongodb's filesystem.
   * 
   * @param fileName the file name.
   * @return the File of the image or <code>null</code> if the file does not exist.
   */

  public GridFSDBFile getFile(String fileName) {
    return data.getFiles().findOne(fileName);
  }

  /**
   * gets a file from the mongodb's filesystem and returns the {@link InputStream}.
   * 
   * @param fileName the file name.
   * @return the InputStream
   * @throws DataException if the file does not exist.
   */
  public InputStream getFileAsStream(String fileName) {

    GridFSDBFile file = getFile(fileName);
    if (file == null) {
      throw new DataException("file " + fileName + " not found.");
    }

    return file.getInputStream();
  }

  /**
   * gets a file from the mongodb's filesystem and returns the {@link InputStream}.
   * 
   * @param file the {@link JmmoFile}. Not null.
   * @return the InputStream
   * @throws DataException if the image file does not exist.
   */
  public InputStream getFileAsStream(JmmoFile file) {
    Objects.requireNonNull(file);
    return getFileAsStream(file.getFilename());
  }

  /**
   * returns a list of all files.
   * 
   * @return
   */
  public List<JmmoFile> getFiles() {
    return data.store().createQuery(JmmoFile.class).asList();
  }

  public JmmoFile getJmmoFile(String filename) {
    return data.store().createQuery(JmmoFile.class)
        .filter(JmmoFile.meta().filename().name() + " = ", filename).get();
  }


  private void requireFileDoesNotExist(String fileName) {
    if (getFile(fileName) != null) {
      throw new DupKeyException("image " + fileName + " already exists");
    }
  }

}
