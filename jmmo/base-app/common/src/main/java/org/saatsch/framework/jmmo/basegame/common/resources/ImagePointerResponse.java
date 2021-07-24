package org.saatsch.framework.jmmo.basegame.common.resources;

import org.saatsch.framework.jmmo.data.api.Pointer;
import java.io.Serializable;

import org.saatsch.framework.jmmo.data.api.model.JmmoFile;

/**
 * Response to a {@link ResolveImagePointerRequest}
 * 
 * @author saatsch
 *
 */
public class ImagePointerResponse implements Serializable {

  private static final long serialVersionUID = 7766038162586391549L;

  private final byte[] content;

  private final Pointer<JmmoFile> pointer;

  public ImagePointerResponse(byte[] content, Pointer<JmmoFile> image) {
    this.content = content;
    this.pointer = image;
  }

  public byte[] getContent() {
    return content;
  }

  public Pointer<JmmoFile> getImagePointer() {
    return pointer;
  }
  
  
  
}
