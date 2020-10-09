package org.saatsch.framework.base.swt.image;

import java.awt.Color;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import jiconfont.icons.font_awesome.FontAwesome;

/**
 * value object that lazily loads a {@link FontAwesome} image, given it's key.
 * 
 * @author saatsch
 *
 */
public class ConstPlusImage {

  private final FontAwesome key;
  
  private Image image;
  
  public ConstPlusImage(FontAwesome key) {
    this.key = key;
  }

  public FontAwesome getKey() {
    return key;
  }
  
  public Image getImage() {
    
    if (image == null) {
      image = new Image(Display.getDefault(), IconFontSwt.buildSwtIcon(key, 64, new Color(0, 0, 0))); 
    }
    
    return image;
    
  }
  
}
