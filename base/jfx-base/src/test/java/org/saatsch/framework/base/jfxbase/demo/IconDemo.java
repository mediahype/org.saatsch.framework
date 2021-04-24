package org.saatsch.framework.base.jfxbase.demo;

import org.junit.Test;

import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.javafx.IconFontFX;
import jiconfont.javafx.IconNode;

public class IconDemo extends AbstractDemo{

  @Override
  protected void fill(VBox root) {
    
    IconFontFX.register(FontAwesome.getIconFont());
    
    // icon node
    IconNode iconNode = new IconNode(FontAwesome.SMILE_O);
    iconNode.setIconSize(40);
    iconNode.setFill(Color.DARKGREEN);
    root.getChildren().add(iconNode);
    
    // image
    Label label = new Label();
    label.setGraphic(new ImageView(IconFontFX.buildImage(FontAwesome.BTC, 80,  Color.DARKOLIVEGREEN)));
    root.getChildren().add(label);
    
  }

  
  @Test
  public void test() {
    Application.launch(this.getClass());
  }
  
}
