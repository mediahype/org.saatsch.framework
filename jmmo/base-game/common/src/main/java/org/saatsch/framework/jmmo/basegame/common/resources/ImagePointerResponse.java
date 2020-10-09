package org.saatsch.framework.jmmo.basegame.common.resources;

import org.saatsch.framework.jmmo.data.api.Pointer;
import java.io.Serializable;

import org.saatsch.framework.jmmo.data.api.model.JmmoImage;

/**
 * Response to a {@link ResolveImagePointerRequest}
 * 
 * @author saatsch
 *
 */
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
