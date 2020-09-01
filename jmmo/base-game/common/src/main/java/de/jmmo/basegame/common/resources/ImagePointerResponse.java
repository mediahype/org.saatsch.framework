package de.jmmo.basegame.common.resources;

import de.jmmo.data.api.Pointer;
import java.io.Serializable;

import de.jmmo.data.api.model.JmmoImage;

public class ImagePointerResponse implements Serializable {

  private static final long serialVersionUID = 7766038162586391549L;

  private final byte[] content;

  private final Pointer<JmmoImage> pointer;

  public ImagePointerResponse(byte[] content, Pointer<JmmoImage> image) {
    this.content = content;
    this.pointer = image;
  }

  public byte[] getContent() {
    return content;
  }

  public Pointer<JmmoImage> getImagePointer() {
    return pointer;
  }
  
  
  
}
