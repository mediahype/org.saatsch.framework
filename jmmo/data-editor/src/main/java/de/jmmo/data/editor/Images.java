package de.jmmo.data.editor;

import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;

public class Images {

  public static Image get(Img image) {
    
    switch (image) {
      case INCOMING_REF:
        return SWTResourceManager.getImage(Images.class, "incoming.png");
      case OUTGOING_REF:
        return SWTResourceManager.getImage(Images.class, "outgoing.png");
      default:
        return SWTResourceManager.getMissingImage();
    }
    
  }
  
}
