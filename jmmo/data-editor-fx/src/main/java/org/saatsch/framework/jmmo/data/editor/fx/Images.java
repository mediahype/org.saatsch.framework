package org.saatsch.framework.jmmo.data.editor.fx;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class Images {

  private static Image incoming;
  private static Image outgoing;


  public static ImageView get(Img image) {
    switch (image) {
      case INCOMING_REF:
        return load(incoming, "/img/incoming.png");
      case OUTGOING_REF:
        return load(outgoing, "/img/outgoing.png");
      default:
        throw new RuntimeException("image not found");
    }
  }

  private static ImageView load(Image into, String fileName) {
    if (into == null) {
      into = new Image( fileName);
    }
    return new ImageView(into);
  }

}
