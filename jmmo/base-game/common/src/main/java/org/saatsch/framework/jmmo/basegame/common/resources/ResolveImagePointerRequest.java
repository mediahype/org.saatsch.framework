package org.saatsch.framework.jmmo.basegame.common.resources;

import java.util.Objects;

import org.saatsch.framework.jmmo.data.api.Pointer;
import org.saatsch.framework.jmmo.data.api.model.JmmoImage;

/**
 * asks the server to resolve a pointer to an image and send the image back.
 *
 */
public class ResolveImagePointerRequest extends GiveResourceRequest {

  private static final long serialVersionUID = -3875593093979399670L;

  private final Pointer<JmmoImage> pointer;

  public ResolveImagePointerRequest(Pointer<JmmoImage> pointer) {
    super();
    this.pointer = Objects.requireNonNull(pointer);
  }

  public Pointer<JmmoImage> getPointer() {
    return pointer;
  }
  
  
  
}
