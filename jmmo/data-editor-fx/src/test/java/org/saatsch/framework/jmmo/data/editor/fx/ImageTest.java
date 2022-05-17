package org.saatsch.framework.jmmo.data.editor.fx;

import javafx.embed.swing.JFXPanel;
import org.junit.Assert;
import org.junit.Test;

public class ImageTest {

  @Test
  public void canLoadImage(){

    JFXPanel x = new JFXPanel();
    Assert.assertNotNull(Images.get(Img.INCOMING_REF));

  }

}
