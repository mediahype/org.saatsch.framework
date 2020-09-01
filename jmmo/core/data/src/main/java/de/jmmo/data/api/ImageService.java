package de.jmmo.data.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import de.jmmo.cdi.container.JmmoContext;
import de.jmmo.data.DataException;
import de.jmmo.data.api.model.JmmoImage;
import de.jmmo.data.mongo.MorphiaMongoDataSink;
import de.osrg.base.UserException;

/**
 * Image service for use on a machine that has direct connection to the database (server).
 * 
 * @author saatsch
 *
 */

public class ImageService {


  private MorphiaMongoDataSink data = JmmoContext.getBean(MorphiaMongoDataSink.class);


  /**
   * create a new Image from a fully qualified filename on the file system
   * 
   * @param fileName
   * @return 
   */
  public JmmoImage newImage(String fileName) {
    try {
      GridFSInputFile createFile = data.getFiles().createFile(new File(fileName));
      return _newImage(createFile, fileName);
    } catch (IOException e) {
      throw new UserException("Problems saving image: ", e);
    }
  }

  public JmmoImage newImage(InputStream image, String name) {
    GridFSInputFile createFile = data.getFiles().createFile(image);
    return _newImage(createFile, name);
  }

  private JmmoImage _newImage(GridFSInputFile file, String fileName) {
    file.setFilename(imageNameOf(fileName));
    file.save();
    JmmoImage img = new JmmoImage();
    img.setFilename(fileName);
    data.save(img);
    return img;

  }

  /**
   * gets an image file from the mongodb's filesystem.
   * 
   * @param fileName the file name.
   * @return the File of the image or <code>null</code> if the file does not exist.
   */
  public GridFSDBFile getImage(String fileName) {
    return data.getFiles().findOne(imageNameOf(fileName));
  }

  /**
   * gets an image file from the mongodb's filesystem and returns the {@link InputStream}.
   * 
   * @param fileName the file name.
   * @return the InputStream
   * @throws DataException if the image file does not exist.
   */
  public InputStream getImageAsStream(String fileName) {

    GridFSDBFile image = getImage(fileName);
    if (image == null) {
      throw new DataException("image file " + fileName + " not found.");
    }

    return image.getInputStream();
  }

  /**
   * gets an image file from the mongodb's filesystem and returns the {@link InputStream}.
   * 
   * @param image the {@link JmmoImage}. Not null.
   * @return the InputStream
   * @throws DataException if the image file does not exist.
   */
  public InputStream getImageAsStream(JmmoImage image) {
    Objects.requireNonNull(image);
    return getImageAsStream(image.getFilename());
  }

  /**
   * returns a list of all images.
   * 
   * @return
   */
  public List<JmmoImage> getImages() {
    return data.store().createQuery(JmmoImage.class).asList();
  }

  public JmmoImage getJmmoImage(String filename){
    return data.store().createQuery(JmmoImage.class).filter(JmmoImage.meta().filename().name() + " = ", filename ).get();
  }

  private static String imageNameOf(String fileName) {
    return "images://" + fileName;
  }

}
