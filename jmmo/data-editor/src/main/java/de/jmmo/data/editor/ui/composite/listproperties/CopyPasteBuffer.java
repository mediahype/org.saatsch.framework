package de.jmmo.data.editor.ui.composite.listproperties;

import org.joda.beans.Property;

public class CopyPasteBuffer {

  private Property<Object> buffer;

  
  public Property<Object> getBuffer() {
    return buffer;
  }

  public void setBuffer(Property<Object> buffer) {
    this.buffer = buffer;
  }
  
}
