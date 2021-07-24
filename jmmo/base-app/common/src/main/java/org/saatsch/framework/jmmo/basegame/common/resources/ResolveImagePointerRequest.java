package org.saatsch.framework.jmmo.basegame.common.resources;

import java.util.Objects;

import org.saatsch.framework.jmmo.data.api.Pointer;
import org.saatsch.framework.jmmo.data.api.model.JmmoFile;

/**
 * asks the server to resolve a pointer to an image and send the image back.
 *
 */
public class ResolveImagePointerRequest extends GiveResourceRequest {

  private static final long serialVersionUID = -3875593093979399670L;

  private final Pointer<JmmoFile> pointer;

  public ResolveImagePointerRequest(Pointer<JmmoFile> pointer) {
    super();
    this.pointer = Objects.requireNonNull(pointer);
  }

  public Pointer<JmmoFile> getPointer() {
    return pointer;
  }
  
  
  
}
